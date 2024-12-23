// src/mocks/handlers.js
import {rest} from 'msw';
import seanceTypesData from '../resources/getSeanceTypes_ShouldReturnSeanceTypesTEST.json';
import seanceRepertoires from '../resources/getSeanceRepertoires_ShouldReturnSeanceRepertoiresTEST.json';
import allPhotoFromAllIn from '../resources/getAllPhotoFromAllInToJsonTEST.json';

export const handlers = [
    // Mock the GET request to the specified URL
    rest.get('http://localhost:9090/photos', (req, res, ctx) => {
        return res(
            ctx.status(200),
            ctx.json(allPhotoFromAllIn)
        );
    }),
    rest.get('http://localhost:9090/api/seance-types', (req, res, ctx) => {
        return res(
            ctx.status(200),
            ctx.json(seanceTypesData)
        );
    }),
    rest.get('http://localhost:9090/api/seance-repertoire', (req, res, ctx) => {
        return res(
            ctx.status(200),
            ctx.json(seanceRepertoires)
        );
    }),
    rest.get('http://localhost:9090/api/photos?type=ALL_IN&repertoire=00-CheckIn-2', (req, res, ctx) => {
        return res(
            ctx.status(200),
            ctx.json(seanceRepertoires)
        );
    }),
    rest.get('http://localhost:9090/photos?type=ALL_IN&repertoire=00-CheckIn-2', (req, res, ctx) => {
        return res(
            ctx.status(200),
            ctx.json(allPhotoFromAllIn)
        );
    }),
];
