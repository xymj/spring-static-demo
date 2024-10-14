import { BASE_URL_API } from "../../../constants/constants";

export const URLs = {
  TRANSACTIONS: `monitor/transactions`,
  API_KEY: `api_key`,
  FILES: `files`,
  VERSION: `version`,
  MESSAGES: `monitor/messages`,
  STORE: `store`,
  BUILD: `build`,
} as const;

export function getURL(key: keyof typeof URLs, params: any = {}) {
  let url = URLs[key];
  Object.keys(params).forEach((key) => (url += `/${params[key]}`));
  return `${BASE_URL_API}${url.toString()}`;
}

export type URLsType = typeof URLs;
