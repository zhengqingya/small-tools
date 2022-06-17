import Cookies from "js-cookie";

const TokenKey = "Authorization";

export function getToken () {
  return Cookies.get(TokenKey);
}

export function setToken (token) {
  return Cookies.set(TokenKey, token);
}

export function removeToken () {
  return Cookies.remove(TokenKey);
}

export function removeAll () {
  // 这里清空所有 Cookies
  var cookies = document.cookie.split(";");
  if (cookies.length > 0)
  {
    for (let i = 0; i < cookies.length; i++)
    {
      let cookie = cookies[i];
      let eqPos = cookie.indexOf("=");
      let key = eqPos > -1 ? cookie.substr(0, eqPos) : cookie;
      Cookies.remove(key);
    }
  }
  return true;
}
