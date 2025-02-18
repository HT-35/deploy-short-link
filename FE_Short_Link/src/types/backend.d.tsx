export {};

declare global {
  // type truyền vào fetchApi để call api
  interface IRequest {
    url: string;
    method: "GET" | "POST" | "PUT" | "PATCH" | "DELETE";
    body?: Record<string, unknown>;
    queryParams?: Record<string, unknown>;
    useCredentials?: boolean;
    headers?: {
      Authorization?: string | null;
      Token?: string | null;
    };
    nextOption?: Record<string, unknown>;
  }

  // type dữ liệu trả về khi call Api
  interface IBackendRes<T> {
    error?: string | string[];
    message: string;
    statusCode: number | string;
    data?: T;
  }

  interface ICreateLink {
    shortLink: string;
    originalLink: string;
    expireDate: string;
  }

  // type dữ liệu User trả về khi call Api

  export interface IRegisterUser {
    _id: string;
    email: string;
    name: string;
    roles: string;

    isActive: boolean;
    __v: 0;
  }
}
