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


/********************************
      uploadFile  테이블 
********************************/
export const uploadFileDB = (file) => {
  console.log(file);
  return new Promise((resolve, reject) => {
    try {
      const response = axios({
        method: "post",
        url: process.env.REACT_APP_SPRING_IP + "reple/fileUpload",
        headers: {
          "Content-Type": "multipart/form-data",
        },
        processData: false,
        contentType: false,
        data: file,
      });
      resolve(response);
    } catch (error) {
      reject(error);
    }
  });
};


export const uploadImageDB = (file) => {
  console.log(file);
  return new Promise((resolve, reject) => {
    try {
      const response = axios({
        method: "post",
        url: process.env.REACT_APP_SPRING_IP + "reple/imageUpload",
        headers: {
          "Content-Type": "multipart/form-data",
        },
        processData: false,
        contentType: false,
        data: file,   // 스프링부트와 연동 시 @RequestBody 사용
      });
      resolve(response);
    } catch (error) {
      reject(error);
    }
  });
};


/********************************
      qna  테이블 
********************************/
export const qnaListDB = (board) => {
  console.log("qnaListDB 호출")

  return new Promise((resolve, reject) => {
    try {
      console.log(board);
      // axios - 비동기 요청 처리
      // [ajax - fetch(브라우저) - axios(NodeJS - oracle서버연동)]
      const response = axios({// 3000번 서버에서 8000서버로 요청을 함 -> 네트워크(다른서버 - CORS이슈)
        method: "GET",
        url: process.env.REACT_APP_SPRING_IP + "reple/qnaList",
        params: board, //쿼리스트링은 header에 담김 - get방식 // 스프링부트 사용 시 @RequestParam 사용
      });
      resolve(response);
    } catch (error) {
      reject(error);
    }
  });
};

export const qnaDetailDB = (board) => {
  console.log("qnaDetailDB 호출")
  console.log(board)  // 키 이름이 대소문자인지 확인 <- 소문자로 해야한다(백엔드와 연동)

  return new Promise((resolve, reject) => {
    try {
      console.log(board);
      // axios - 비동기 요청 처리
      // [ajax - fetch(브라우저) - axios(NodeJS - oracle서버연동)]
      const response = axios({// 3000번 서버에서 8000서버로 요청을 함 -> 네트워크(다른서버 - CORS이슈)
        method: "GET",
        url: process.env.REACT_APP_SPRING_IP + "reple/qnaDetail",
        params: board, //쿼리스트링은 header에 담김 - get방식 // 스프링부트 사용 시 @RequestParam 사용
      });
      resolve(response);
    } catch (error) {
      reject(error);
    }
  });
};

export const qnaInsertDB = (board) => {
  console.log("qnaInsertDB 호출")
  console.log(board)    // fileNames = ['man1.png', 'man2.png', ...]

  return new Promise((resolve, reject) => {
    try {
      const response = axios({
        method: "POST", //@RequestBody
        url: process.env.REACT_APP_SPRING_IP + "reple/qnaInsert",
        data: board, //post방식으로 전송시 반드시 data속성으로 파라미터 줄것
      });
      resolve(response);
    } catch (error) {
      reject(error);
    }
  });
};

export const qnaUpdateDB = (board) => {
  console.log("qnaUpdateDB 호출")
  // 대소문자 구분 어떻게 할 것인가?
  // 파라미터는 소문자로
  // 리턴값은 대문자로
  // 아니면 둘다 대문자로 할까?
  return new Promise((resolve, reject) => {
    try {
      const response = axios({
        method: "POST", //@RequestBody
        url: process.env.REACT_APP_SPRING_IP + "reple/qnaUpdate",
        data: board, //post방식으로 전송시 반드시 data속성으로 파라미터 줄것
      });
      resolve(response);
    } catch (error) {
      reject(error);
    }
  });
};

export const qnaDeleteDB = (board) => {
  console.log("qnaDeleteDB 호출")

  return new Promise((resolve, reject) => {
    try {
      const response = axios({
        method: "GET", //@RequestParam
        url: process.env.REACT_APP_SPRING_IP + "reple/qnaDelete",
        params: board, //post방식으로 전송시 반드시 data속성으로 파라미터 줄것
      });
      resolve(response);
    } catch (error) {
      reject(error);
    }
  });
};