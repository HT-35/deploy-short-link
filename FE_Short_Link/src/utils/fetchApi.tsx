/* eslint-disable @typescript-eslint/no-explicit-any */

// Send data JSON
export const sendRequest = async <T,>(props: IRequest) => {
  //type
  const {
    url: originalUrl,
    method,
    body,
    useCredentials = false,
    headers = {},
    nextOption = {},
  } = props;

  // Lọc headers để đảm bảo không có giá trị null hoặc undefined
  const validHeaders = Object.fromEntries(
    Object.entries({
      "content-type": "application/json",
      ...headers,
    }).filter(([, value]) => typeof value === "string")
  );

  const options: any = {
    method: method,
    headers: new Headers(validHeaders as Record<string, string>),
    body: body ? JSON.stringify(body) : null,
    ...nextOption,
  };

  if (useCredentials) options.credentials = "include";

  let url = originalUrl;
  if (
    url?.startsWith("localhost") ||
    url?.startsWith("huytranfullstack.id.vn/api")
  ) {
    url = `http://${url}`;
  }

  return fetch(url, options).then((res) => {
    if (res.ok) {
      return res.json() as T; //generic
    } else {
      return res.json().then(function (json) {
        // to be able to access error status when you catch the error
        return {
          statusCode: res.status,
          message: json?.message ?? "",
          error: json?.error ?? "",
        } as T;
      });
    }
  });
};
