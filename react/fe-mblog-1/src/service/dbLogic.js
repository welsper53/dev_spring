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
