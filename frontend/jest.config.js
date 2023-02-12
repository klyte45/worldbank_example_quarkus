/** @type {import('ts-jest/dist/types').InitialOptionsTsJest} */
import { env } from 'process';
import tsconfig from "./tsconfig.json";
const moduleNameMapper = require("tsconfig-paths-jest")(tsconfig)
env.TEST = "T"
export default {
  preset: 'ts-jest',
  testEnvironment: 'node',
  moduleNameMapper
};