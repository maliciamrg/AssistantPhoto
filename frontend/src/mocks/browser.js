// src/mocks/browser.js
import { setupWorker } from 'msw';
import { handlers } from './handlers';

// Set up the worker with the handlers
export const worker = setupWorker(...handlers);
