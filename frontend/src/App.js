import React, { useState, useEffect } from 'react';
import axios from 'axios';

const App = () => {
    const [seanceTypes, setSeanceTypes] = useState([]);
    const [selectedSeanceType, setSelectedSeanceType] = useState('');
    const [seanceRepertoires, setSeanceRepertoires] = useState([]);
    const [selectedSeanceRepertoire, setSelectedSeanceRepertoire] = useState('');
    const [dashboardVisible, setDashboardVisible] = useState(false);
    const [photos, setPhotos] = useState([]);
    const apiUrl = process.env.REACT_APP_API_URL;

    // Fetch seance types on component mount
    useEffect(() => {
        axios.get(`${apiUrl}/seance-types`)
            .then(response => setSeanceTypes(response.data.map(type => ({
                id: type.id,
                name: type.name
            }))))
            .catch(error => console.error('Error fetching seance types:', error));
    }, [apiUrl]);

    // Fetch seance repertoires when a seance type is selected
    useEffect(() => {
        if (selectedSeanceType) {
            axios.get(`${apiUrl}/seance-repertoire?type=${selectedSeanceType}`)
                .then(response => setSeanceRepertoires(response.data.map(repertoire => ({
                    id: repertoire.path,
                    name: repertoire.path
                }))))
                .catch(error => console.error('Error fetching seance repertoires:', error));
        } else {
            setSeanceRepertoires([]);
        }
    }, [apiUrl,selectedSeanceType]);

    // Fetch photos for the dashboard
    useEffect(() => {
        if (dashboardVisible) {
            axios.get(`${apiUrl}/photos?type=${selectedSeanceType}&repertoire=${selectedSeanceRepertoire}`)
                .then(response => setPhotos(response.data))
                .catch(error => console.error('Error fetching photos:', error));
        }
    }, [apiUrl,dashboardVisible, selectedSeanceType, selectedSeanceRepertoire]);

    const handleStart = () => {
        if (selectedSeanceType && selectedSeanceRepertoire) {
            setDashboardVisible(true);
        } else {
            alert('Please select both a seance type and repertoire.');
        }
    };

    const handleLogout = () => {
        setDashboardVisible(false);
        setSelectedSeanceType('');
        setSelectedSeanceRepertoire('');
    };

    const handlePhotoAction = (photoId, action) => {
        console.log(`Photo ID: ${photoId}, Action: ${action}`);
    };

    return (
        <div>
            {dashboardVisible ? (
                <div>
                    <header>
                        <nav>
                            <button onClick={() => console.log('Parametrer clicked')}>Parametrer</button>
                            <button onClick={() => console.log('Action clicked')}>Action</button>
                            <button onClick={handleLogout}>Logout</button>
                        </nav>
                    </header>
                    <main>
                        <div className="carousel">
                            {photos.map(photo => (
                                <div key={photo.id} className="photo-item" onContextMenu={(e) => {
                                    e.preventDefault();
                                    const action = prompt('Choose an action: flag, unflag, star');
                                    if (action) handlePhotoAction(photo.id, action);
                                }}>
                                    <img src={photo.url} alt={photo.description} />
                                </div>
                            ))}
                        </div>
                    </main>
                </div>
            ) : (
                <div>
                    <h1>Choose Seance</h1>
                    <div>
                        <label>Seance Type:</label>
                        <select value={selectedSeanceType} onChange={(e) => setSelectedSeanceType(e.target.value)}>
                            <option value="">Select...</option>
                            {seanceTypes.map(type => (
                                <option key={type.id} value={type.id}>{type.name}</option>
                            ))}
                        </select>
                    </div>
                    <div>
                        <label>Seance Repertoire:</label>
                        <select value={selectedSeanceRepertoire} onChange={(e) => setSelectedSeanceRepertoire(e.target.value)}>
                            <option value="">Select...</option>
                            {seanceRepertoires.map(name => (
                                <option key={name.id} value={name.id}>{name.name}</option>
                            ))}
                        </select>
                    </div>
                    <button onClick={handleStart}>Start</button>
                </div>
            )}
        </div>
    );
};

export default App;
