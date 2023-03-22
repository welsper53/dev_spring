import axios from "axios";

export const jsonMemberListDB = (member) => {
  console.log("jsonMemberListDB 호출")

  return new Promise((resolve, reject) => {
    try {
        const response = axios({
          method: "get",
          url: process.env.REACT_APP_SPRING_IP + "member/jsonMemberList",
          params: member,
        });
        resolve(response);
    } catch (error) {
        reject(error);
    }
  });
};

export const deptInsertDB = (dept) => {
  console.log("deptInsertDB 호출")

  return new Promise((resolve, reject) => {
    try {
        const response = axios({
          method: "POST",   // @RequestBody
          url: process.env.REACT_APP_SPRING_IP + "dept/deptInsert",
          // POST방식으로 전송 시 반드시 data속성으로 파라미터 줄것
          data: dept
        });
        resolve(response);
    } catch (error) {
        reject(error);
    }
  });
};
export const deptListDB = (dept) => {
  console.log("deptListDB 호출")

  return new Promise((resolve, reject) => {
    try {
        const response = axios({
          method: "GET",
          url: process.env.REACT_APP_SPRING_IP + "dept/deptList",
          // GET방식으로 전송 시 쿼리스트링은 header에 담긴다
          params: dept,
        });
        resolve(response);
    } catch (error) {
        reject(error);
    }
  });
};