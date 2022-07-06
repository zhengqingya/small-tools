/*
 * @Description:
 * @Version: 1.0
 * @Autor: yanxin
 * @Date: 2020-01-03 14:36:02
 * @LastEditors: yanxin
 * @LastEditTime: 2020-08-14 09:20:56
 */
const modulesFiles = require.context('./base', true, /\.vue$/)
let modules = modulesFiles.keys().reduce((modules, modulePath) => {
  const moduleName = modulePath.replace(/^\.\/(.*)\.\w+$/, '$1')
  const value = modulesFiles(modulePath)
  modules[moduleName] = value.default
  return modules
}, {})
import Tinymce from './Tinymce/index'
modules['Tinymce'] = Tinymce
export default modules
