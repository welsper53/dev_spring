export const SET_AUTH = "USER_AUTH/SET_AUTH";

export const setAuth = (auth, googleAuthProvider) => {
  return {
    type: SET_AUTH,
    auth: auth,
    googleProvider: googleAuthProvider
  }
}