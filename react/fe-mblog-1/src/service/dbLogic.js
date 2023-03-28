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
      resolve(response); // 요청 처리 성공했을 때
    } catch (error) {
      reject(error);  // 요청 처리 실패했을 때
    }
  });
};

/********************************
      TerrGYM DEPT 테이블 
********************************/
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
        resolve(response); // 요청 처리 성공했을 때
    } catch (error) {
        reject(error);  // 요청 처리 실패했을 때
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
      resolve(response); // 요청 처리 성공했을 때
    } catch (error) {
      reject(error);  // 요청 처리 실패했을 때
    }
  });
};
export const deptUpdateDB = (dept) => {
  console.log("deptUpdateDB 호출")

  return new Promise((resolve, reject) => {
    console.log(dept);
    try {
      const response = axios({
        method: "post", //@RequestBody
        url: process.env.REACT_APP_SPRING_IP + "dept/deptUpdate",
        data: dept, //post방식으로 전송시 반드시 data속성으로 파라미터 줄것
      });
      resolve(response); //요청 처리가 성공했을 때
    } catch (error) {
      reject(error); //요청 처리 실패했을 때
    }
  });
};
export const deptDeleteDB = (deptno) => {
  console.log("deptDeleteDB 호출")

  return new Promise((resolve, reject) => {
    try {
      const response = axios({
        method: "GET",
        url: process.env.REACT_APP_SPRING_IP + "dept/deptDelete",
        // GET방식으로 전송 시 쿼리스트링은 header에 담긴다
        params: deptno,
      });
      resolve(response); // 요청 처리 성공했을 때
    } catch (error) {
      reject(error);  // 요청 처리 실패했을 때
    }
  });
};


/********************************
      mBlog 멤버 테이블 
********************************/
export const memberInsertDB = (member) => {
  console.log("memberInsertDB 호출")
  console.log(member)

  return new Promise((resolve, reject) => {
    try {
        const response = axios({
          method: "POST",   // @RequestBody
          url: process.env.REACT_APP_SPRING_IP + "member/memberInsert",
          // POST방식으로 전송 시 반드시 data속성으로 파라미터 줄것
          data: member
        });
        resolve(response); // 요청 처리 성공했을 때
    } catch (error) {
        reject(error);  // 요청 처리 실패했을 때
    }
  });
};
export const memberListDB = (member) => {
  console.log("memberListDB 호출")
  console.log(member)
  
  return new Promise((resolve, reject) => {
    try {
      const response = axios({
        method: "GET",
        url: process.env.REACT_APP_SPRING_IP + "member/memberList",
        // GET방식으로 전송 시 쿼리스트링은 header에 담긴다
        params: member,
      });
      resolve(response); // 요청 처리 성공했을 때
    } catch (error) {
      reject(error);  // 요청 처리 실패했을 때
    }
  });
};
export const memberUpdateDB = (member) => {
  console.log("memberUpdateDB 호출")
  console.log(member)

  return new Promise((resolve, reject) => {
    try {
      const response = axios({
        method: "post", //@RequestBody
        url: process.env.REACT_APP_SPRING_IP + "dept/memberUpdate",
        data: member, //post방식으로 전송시 반드시 data속성으로 파라미터 줄것
      });
      resolve(response); //요청 처리가 성공했을 때
    } catch (error) {
      reject(error); //요청 처리 실패했을 때
    }
  });
};
export const memberDeleteDB = (mem_no) => {
  console.log("memberDeleteDB 호출")
  console.log(mem_no)

  return new Promise((resolve, reject) => {
    try {
      const response = axios({
        method: "GET",
        url: process.env.REACT_APP_SPRING_IP + "dept/memberDelete",
        // GET방식으로 전송 시 쿼리스트링은 header에 담긴다
        params: mem_no,
      });
      resolve(response); // 요청 처리 성공했을 때
    } catch (error) {
      reject(error);  // 요청 처리 실패했을 때
    }
  });
};