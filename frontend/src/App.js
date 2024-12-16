import React, { useEffect, useState } from "react";
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";

// Carousel component to display the images
function Carousel({ photos }) {
  const settings = {
    dots: true,
    infinite: true,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
  };

  return (
      <div className="carousel">
        <Slider {...settings}>
          {photos.map((photo, index) => (
              <div key={index}>
                <img src={photo.thumbnail} alt={`Thumbnail ${index}`} className="carousel-image" />
              </div>
          ))}
        </Slider>
      </div>
  );
}

// Main component to fetch and display photos
function PhotoCarousel() {
  const [photos, setPhotos] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetch("http://localhost:8080/photos?seanceType=ALL_IN")  // Replace with your actual API URL
        .then((response) => response.json())
        .then(data => {
            setPhotos(data); // Store the fetched photos data
            setLoading(false); // Stop loading
        })
        .catch((error) => {
          console.error("Error fetching photos:", error);
          setLoading(false);
        });
  }, []);

  if (loading) {
    return <div>Loading...</div>;
  }

  return (
      <div>
        <h2>Photo Carousel</h2>
          <div className="carousel-container">
              <div className="carousel">
                  {photos.map((photo, index) => (
                      <img
                          key={index}
                          src={photo.thumbnail} // This is the Base64 string
                          alt={`Thumbnail ${index}`}
                          width="100" // You can adjust the size of the thumbnail here
                      />
                  ))}
              </div>
          </div>
      </div>
  );
}

export default PhotoCarousel;
