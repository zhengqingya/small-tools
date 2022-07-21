// 验证码类型声明
export interface Captcha {
  img: string
  uuid: string
}

export interface LoginFormData {
  username: string
  password: string
  grant_type: string
  code: string
  uuid: string
}

export interface LoginResponseData {
  tokenType: string
  value: string
}
