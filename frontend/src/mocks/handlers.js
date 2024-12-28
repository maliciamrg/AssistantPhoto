// src/mocks/handlers.js
import {rest} from 'msw';
import seanceTypesData from '../resources/getSeanceTypes_ShouldReturnSeanceTypesTEST.json';
import seanceRepertoires from '../resources/getSeanceRepertoires_ShouldReturnSeanceRepertoiresTEST.json';
import allPhotoFromAllIn from '../resources/getAllPhotoFromAllInToJsonTEST.json';

// Define the API URL from the environment variable
const apiUrl = process.env.REACT_APP_API_URL || 'http://localhost:8080/api'; // Default to localhost in case the variable is missing

export const handlers = [
    // Mock the GET request to the specified URL
    rest.get(`${apiUrl}/seance-types`, (req, res, ctx) => {
        return res(
            ctx.status(200),
            ctx.json(seanceTypesData)
        );
    }),
    rest.get(`${apiUrl}/seance-repertoire`, (req, res, ctx) => {
        return res(
            ctx.status(200),
            ctx.json(seanceRepertoires)
        );
    }),
    rest.get(`${apiUrl}/photos?type=ALL_IN&repertoire=00-CheckIn-2`, (req, res, ctx) => {
        return res(
            ctx.status(200),
            ctx.json(allPhotoFromAllIn)
        );
    }),
];
