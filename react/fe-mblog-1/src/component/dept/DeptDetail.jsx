import React, { useCallback, useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import {  deptDeleteDB, deptListDB, deptUpdateDB } from '../../service/dbLogic'
import { validateDName } from '../../service/validateLogic'
import BlogFooter from '../include/BlogFooter'
import BlogHeader from '../include/BlogHeader'
import styled from 'styled-components'
import { Button, Card, Form, Modal } from 'react-bootstrap'
import { MyInput, MyLabel, MyLabelAb } from '../styles/FormStyle'

const DivDeptBody = styled.div`
  display: flex;
  flex-direction: column;
  margin: 0px 20px;
`
const Img = styled.img`
  width: 100%;
  height: 100%;
  object-fit: cover;
`
const DeptUploading = styled.div`
  display: flex;
  width: 200px;
  height: 250px;
  align-items: center;
  overflow: hiddent;
  margin: 10px auto;
`;


const DeptDetail = ({imageUploader}) => {
  // 부서번호를 클릭했을 때 해시값으로 전달된 부서번호 담기
  // 사용자가 부서번호를 선택할때마다 변경되니까 
  // useEffect에서 의존배열인자로 사용한다
  const { deptno } = useParams()  // App.jsx의 Route path 해시값으로 넘어온다 -> 바뀐다
  // 오라클 서버에서 파라미터로 넘어온 부서번홀르 가지고 한 건을 조회한 후에 담기
  const [ dept, setDept ] = useState({
    DEPTNO: 0,
    DNAME: '',
    LOC: '',
    FILENAME: '',
    FILEURL: '',
  })
  const [ dname, setDname ] = useState()
  const [ loc, setLoc ] = useState()
  const [comment, setComment] = useState({
    deptno: "",
    dname: "",
    loc: "",
  })
  const [star, setStar] = useState({
    deptno: "*",
    dname: "*",
    loc: "*",
  })
  // 수정화면 모달 마운트(화면에 출력) 여부 결정
  // - false 미출력, true 출력
  const [show, setShow] = useState(false)
  const [files, setFiles] = useState({filename:null, fileurl:null})
  const handleShow = () => setShow(true)

  // 리액트에서는 메모이제이션 컨벤션
  // useMemo와 useCallback - 첫번째 파라미터 함수가 두번째 파라미터 의존성 배열임
  // 차이점: useMemo는 값을 반환하고, useCallback은 함수를 반환한다
  // 리렌더링은 언제 일어나지?
  // -> 1. state변경, 2. props변경, 3. 부모컴포넌트가 변경
  const handleDname = useCallback((value) => {
    //console.log(value)
    setDname(value)
  },[])
  // 아래와 같이 함수를 선언하면 DeptDetail 컴포넌트가 마운트 될 때마다 주소번지가 바뀐다
  // 함수의 구현내용이 변화가 없는 경우라면 한 법 생성된 주소번지를 계속 가지고 있어도 되지 않을까?
  // 그러면 이것을 기억해줘
  // -> cache
  // 필요할 때 새로 생성하지 말고 cache에 있는 함수를 불러줘
  // 이렇게 처리할 때 useCallback 사용한다
  const handleLoc = useCallback((value) => {
    //console.log(value)
    setLoc(value)
  },[])

  const navigate = useNavigate()
  const deptList = () => {
    navigate("/dept/0")
  }

  useEffect(() => {
    // 파라미터로 넘어오는 deptno가 바뀌면 다시 실행된다
    const asyncDB = async() => {
      const res = await deptListDB({deptno: deptno})
      console.log(res.data)
      const result = JSON.stringify(res.data)
      const jsonDoc = JSON.parse(result)
      setDept({DEPTNO: jsonDoc[0].DEPTNO,
              DNAME: jsonDoc[0].DNAME,
              LOC: jsonDoc[0].LOC,
              FILENAME: jsonDoc[0].FILENAME,
              FILEURL: jsonDoc[0].FILEURL
            })
    }
    asyncDB()

    return () => {
      // 언마운트 될 때 처리할 일이 있으면 여기에 코딩할 것
    }
  },[deptno])  // deptno가 변경될 때마다 함수가 실행된다

  // 이미지 파일이 없을 경우 더미 이미지 설정
  if (!dept.FILEURL) {
    dept.FILEURL="http://via.placeholder.com/200X250";
  }
  console.log(dept)


  // 이미지파일 첨부 구현
  const imgChange = async (event) => {
    const uploaded = await imageUploader.upload(event.target.files[0])
    console.log(uploaded)

    setFiles({
      filename: uploaded.public_id+"."+uploaded.format,
      fileurl: uploaded.url
    })

    // input의 이미지 객체 얻어오기
    const upload = document.querySelector("#dimg")
    // 이미지를 집어넣을 곳의 부모태그
    const holder = document.querySelector("#uploadImg")
    const file = upload.files[0]

    const reader = new FileReader()
    reader.onload = (event) => {
      const img = new Image()
      img.src = event.target.result

      if(img.width > 150) {
        img.width = 150
      }
      holder.innerHTML = "";
      holder.appendChild(img)
    }
    reader.readAsDataURL(file)
    
    return false
  }

  const validate = (key, e) => {
    console.log('validate: ' + key)
    let result;

    if (key === 'dname') {
      result = validateDName(e);
    }

    setComment({...comment, [key]:result})
    if (result) {
      if(result === '') {
        setStar({...star, [key]:''})
      } else {
        setStar({...star, [key]:'*'})
      }
    } else {
      setStar({...star, [key]:''})
    }
  }

  // 부서 정보 수정
  // 스프링 부트와 리액트 연동하기 - @RequestBody사용해서 JSON포맷으로 넘김
  const handleClose = () => setShow(false)
  const deptUpdate = async () => {
    const dept = {
			deptno,
			dname,
			loc,
			filename: files.filename,
			fileurl:files.fileurl
		}

    const res = await deptUpdateDB(dept)

    if (!res.data) {
      console.log("부서수정에 실패")
    }
    else {
      console.log("부서수정에 성공")
      // 성공 시 부서목록 새로고침 처리할 것
      // -> location.reload() 쓰지 말것
      // => SPA 컨벤션
      // : useEffect - 의존성 배열을 연습할 수 있다
      handleClose() // 모달창 닫기

      // 부서목로 새로고침
      navigate("/dept/1")
    }
  } // end of deptUpdate
  

  const deptDelete = () => {
    console.log("deptDelete 호출")

    const asyncDB = async() => {
      const res = deptDeleteDB({deptno: deptno}) 
      console.log(res.data)
      navigate("/dept/0")
    }
    asyncDB()

  } // end of deptDelete


  return (
    <>
      <BlogHeader />
      <div className='container'>
        <div className="page-header">
          <div className="page-header">
            <h2>부서관리&nbsp;<i className="fa-solid fa-angles-right"></i>&nbsp;<small>상세보기</small></h2>
            <hr />
          </div>      
          <Card style={{width: '58rem'}}>
            <Card.Body>
              <Card.Img style={{width:'250px'}} src={`${dept.FILEURL}`} alt="Card image" />
              <DivDeptBody>
                <Card.Title>{dept.DEPTNO}</Card.Title>
                <Card.Text>{dept.DNAME}</Card.Text>
                <Card.Text>{dept.LOC}</Card.Text>
              </DivDeptBody>
            </Card.Body>
            <div>
              <Button onClick={handleShow}>수정</Button>
              &nbsp;
              <Button onClick={deptDelete}>삭제</Button>
              &nbsp;
              <Button onClick={deptList}>부서목록</Button>
            </div>
          </Card>
        </div>

        {/* ========================== [[ 부서수정 Modal ]] ========================== */}
        <Modal show={show} onHide={handleClose} animation={false}>
          <Modal.Header closeButton>
            <Modal.Title>부서등록</Modal.Title>
          </Modal.Header>
          <Modal.Body>
          <div style={{display:'flex', flexWrap:'wrap', justifyContent:'center'}}>
            <div style={{display:'flex'}}>
              <MyLabel>부서번호<span style={{color:'red'}}>{star.deptno}</span>
                <MyInput type="text" name="deptno" placeholder="Enter 부서번호" />
                <MyLabelAb>{comment.deptno}</MyLabelAb>
              </MyLabel>
            </div>
            <div style={{display:'flex'}}>
              <MyLabel>부서명<span style={{color:'red'}}>{star.dname}</span>
                <MyInput type="text" name="dname" placeholder="Enter 부서명" onChange={(e) => {(handleDname(e.target.value)); validate('dname', e)}} />
                <MyLabelAb>{comment.dname}</MyLabelAb>
              </MyLabel>
            </div>
            <div style={{display:'flex'}}>
              <MyLabel>지역<span style={{color:'red'}}>{star.loc}</span>
                <MyInput type="text" name="dname" placeholder="Enter 지역" onChange={(e) => {(handleLoc(e.target.value)); validate('loc', e)}} />
                <MyLabelAb>{comment.loc}</MyLabelAb>
              </MyLabel>
            </div>
            <Form.Group className="mb-3" controlId="formBasicOffice">
              <Form.Label>건물이미지</Form.Label>
                <input className="form-control" type="file" accept='image/*' id="dimg" name="dimg" onChange={imgChange}/>
            </Form.Group>
            <DeptUploading id="uploadImg">
              <Img src="http://via.placeholder.com/200X250" alt="미리보기" />
            </DeptUploading>
          </div>

          </Modal.Body>
          <Modal.Footer>
            <Button variant="secondary" onClick={handleClose}>
              닫기
            </Button>
            <Button variant="primary" onClick={deptUpdate}>
              저장
            </Button>
          </Modal.Footer>
        </Modal>     
      {/* ========================== [[ 부서수정 Modal ]] ========================== */}  
      </div>
      <BlogFooter />
    </>
  )
}

export default DeptDetail
