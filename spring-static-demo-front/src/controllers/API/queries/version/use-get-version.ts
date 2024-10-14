import { useDarkStore } from "@/stores/darkStore";
import { useQueryFunctionType } from "@/types/api";
import { api } from "../../api";
import { getURL } from "../../helpers/constants";
import { UseRequestProcessor } from "../../services/request-processor";

interface versionQueryResponse {
  version: string;
  package: string;
}

export const useGetVersionQuery: useQueryFunctionType<
  undefined,
  versionQueryResponse
> = (_, options) => {
  const { query } = UseRequestProcessor();

  const getVersionFn = async () => {
    return {"version": 'version', "package": 'package'};
    // return await api.get<versionQueryResponse>(`${getURL("VERSION")}`);
  };

  const responseFn = async () => {
    // const { data } = await getVersionFn();
    const data = {"version": 'version', "package": 'package'};
    const refreshVersion = useDarkStore.getState().refreshVersion;
    refreshVersion(data.version);
    return data;
  };

  const queryResult = query(["useGetVersionQuery"], responseFn, { ...options });

  return queryResult;
};
