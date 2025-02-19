const domain = "http://localhost:4000";
// const domain = "http://huytranfullstack.id.vn/api";

// const domain = "http://localhost/api"; // docker

// const domain = "http://backend:4000";

export const listAPi = {
  //  quick create link
  createShortLinkQuick: () => `${domain}/link/quick`,
  getAllLinkQuickByUUID: () => `${domain}/link/quick`,
  deleteLinkQuickByUUID: (slug: string) => `${domain}/link/quick/${slug}`,

  // authen create shortlink
  createShortLink: () => `${domain}/link`,
  getAllByEmail: () => `${domain}/link`,
  deleteLink: (slug: string) => `${domain}/link/${slug}`,

  // authen
  login: () => `${domain}/auth/login`,
  register: () => `${domain}/auth/register`,

  // user
  createUser: () => `${domain}/user`,
  getMe: () => `${domain}/user/me`,

  // shortlink
  getDetailShortLink: (slug: string) => `${domain}/shortlink/${slug}`,
};
