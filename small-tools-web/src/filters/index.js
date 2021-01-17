import moment from "moment";

export function dateTimeFilter(date) {
  if (date) {
    return moment(date).format("YYYY-MM-DD HH:mm:ss");
  }
  return "";
}

export function dateFilter(date) {
  if (date) {
    return moment(date).format("YYYY-MM-DD");
  }
  return "";
}

export function monthFilter(date) {
  if (date) {
    return moment(date).format("YYYY-MM");
  }
  return "";
}

export function formatYesNo(value) {
  return value && value === 1 ? "是" : "否";
}
