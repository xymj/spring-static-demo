import { keepPreviousData } from "@tanstack/react-query";
import { useQueryFunctionType } from "../../../../types/api";
import { api } from "../../api";
import { UseRequestProcessor } from "../../services/request-processor";

interface getHealthResponse {
  status: string;
  chat: string;
  db: string;
  folder: string;
  variables: string;
}

export const useGetHealthQuery: useQueryFunctionType<
  undefined,
  getHealthResponse
> = (_, options) => {
  const { query } = UseRequestProcessor();

  /**
   * Fetches the health status of the API.
   *
   * @returns {Promise<AxiosResponse<TransactionsResponse>>} A promise that resolves to an AxiosResponse containing the health status.
   * 
   * getHealthFn 这个异步函数的作用是从后端 API 获取健康检查状态。具体来说：
   * 函数注释说明了其功能是获取 API 的健康状态，并指出返回的是一个 Promise 对象，该对象最终解析为 Axios 库请求得到的数据类型 AxiosResponse<TransactionsResponse>。这里的 TransactionsResponse 可能是指具体的响应数据结构类型，但实际应该替换为健康检查响应的具体类型。
   * 在函数体内部，通过调用 api.get 方法发送 GET 请求至 /health_check 端点。api 变量应当是在外部定义的一个 Axios 实例，用于封装 HTTP 请求操作。
   * 注意到注释提到 /health_check 端点是唯一不需要 /api/v1 前缀的 API 路径，这表明其他 API 调用可能都需要加上这一前缀作为基础路径。
   * 函数最后返回请求结果的 data 属性，即后端返回的实际健康检查数据。
   * 因此，getHealthFn 主要用于监控后端服务的运行状况，常用于服务启动时的自我检测或者持续的健康监测机制中，帮助前端了解后端服务是否正常运行。
   */
  async function getHealthFn() {
    return (await api.get("/health_check")).data;
    // Health is the only endpoint that doesn't require /api/v1
  }

  const queryResult = query(["useGetHealthQuery"], getHealthFn, {
    placeholderData: keepPreviousData,
    refetchInterval: 20000,
    ...options,
  });

  return queryResult;
};


/**
 * 这段 TypeScript 代码定义了一个名为 useGetHealthQuery 的自定义 React Query hook，用于从后端 API 检索并管理健康检查信息。下面详细分析代码的工作原理：

导入模块与接口定义
首先导入了必要的库和类型：

keepPreviousData：来自 @tanstack/react-query，用于配置查询以保留上次的有效数据。
useQueryFunctionType：这是一个自定义类型，用于描述 hook 函数的输入输出类型。
api：这是预设的 Axios 实例，用于发起网络请求。
UseRequestProcessor：这个函数或钩子用于处理请求过程，这里只使用了其中的 query 方法。
接着定义了 getHealthResponse 接口，它描述了健康检查响应的结构，包括五个字段：status, chat, db, folder, 和 variables，每个都是字符串类型。

定义 useGetHealthQuery 钩子
useGetHealthQuery 是一个泛型函数，它的输入参数是未定义的（意味着没有额外的输入），而输出类型是 undefined 到 getHealthResponse 类型的映射关系。这意味着此 hook 不需要任何输入参数，但会返回一个包含健康检查信息的对象。

异步函数 getHealthFn
在这个 hook 内部，定义了一个异步函数 getHealthFn，它负责向 /health_check 终端发起 GET 请求来获取健康检查的状态。注意，文档注释中提到了 /health_check 是唯一不带 /api/v1 前缀的终端，暗示其他所有 API 调用都应带有这个前缀。

使用 query 方法
接下来，使用 UseRequestProcessor 中的 query 方法来创建一个查询实例。query 方法接收三个主要参数：

查询键：["useGetHealthQuery"]，用于标识查询。
查询函数：getHealthFn，当需要数据时将被调用来执行请求。
查询选项：这些选项控制了查询的行为，例如：
placeholderData: 使用 keepPreviousData 来保持上一次有效的数据，直到新的数据可用。
refetchInterval: 设置重新获取数据的时间间隔为 20秒。
其他可选的 options 参数可以覆盖默认设置。
返回值
最后，hook 返回由 query 方法生成的查询结果，这通常包含了当前查询的状态、数据以及重试等方法。

总之，useGetHealthQuery 提供了一种周期性地从服务器获取健康检查信息的方式，并利用 React Query 的强大功能来管理和缓存这些数据，确保应用能够实时更新并显示后端服务的健康状态。
 */