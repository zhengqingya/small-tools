const getters = {
  sidebar: (state) => state.app.sidebar,
  device: (state) => state.app.device,
  token: (state) => state.user.token,
  userId: (state) => state.user.userId,
  avatar: (state) => state.user.avatar,
  username: (state) => state.user.username,
  roles: (state) => state.user.roles,
  permission_routes: (state) => state.permission.routes,
  isDarkTheme: (state) => state.settings.darkTheme,
}
export default getters
