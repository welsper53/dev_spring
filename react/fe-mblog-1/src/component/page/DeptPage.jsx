import React, { useCallback, useEffect, useState } from 'react'
import { Button, Form, Modal, Table } from 'react-bootstrap'
import { deptInsertDB, deptListDB } from '../../service/dbLogic'
import '../css/style.css'
import DeptRow from '../dept/DeptRow'
import BlogHeader from '../include/BlogHeader'
import { useNavigate, useParams } from 'react-router-dom'
import { MyInput, MyLabel, MyLabelAb } from '../styles/FormStyle'
import { validateDName } from '../../service/validateLogic'

const DeptPage = ({imageUploader}) => {
  // Route path="/dept/:gubun"
  // 디폴트 없고 부서등록이 성공하면 1을 돌려준다
  const gubun = useParams()

  // 화면 전환 시 가급적 전체 페이지 리로딩을 하지 않아야한다
  // Navigate훅을 사용하면 된다
  const navigate = useNavigate()

  const [deptList, setDeptList] = useState([])
  const [show, setShow] = useState(false)
  const handleClose = () => setShow(false)
  const handleShow = () => setShow(true)
  const [deptno, setDeptno] = useState(0)
  const [dname, setDname] = useState("")
  const [loc, setLoc] = useState("")
  // filename, fileurl 두개이므로 객체로 선언
  const [files, setFiles] = useState({filename:null, fileurl:null})
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
  const handleDeptno = useCallback((value) => {
    console.log(value)
    setDeptno(value)
  },[])
  const handleDname = useCallback((value) => {
    console.log(value)
    setDname(value)
  },[])
  const handleLoc = useCallback((value) => {
    console.log(value)
    setLoc(value)
  },[])


  // 조건 검색 구현
  const reactSearch = () => {
    console.log("reactSearch 호출")
    // select 콤보에서 선택한 값 담기
    const gubun = document.querySelector("#gubun").value
    // 조건검색에 필요한 문자열 담기
    const keyword = document.querySelector("#keyword").value
    console.log(gubun + ", " + keyword)

    const asyncDB = async () => {
      console.log("asyncDB 호출")
      const res = await deptListDB({gubun, keyword, deptno:0}) // 0: 초기화 의미
      console.log(res.data)
      
      if (res.data) {
        setDeptList(res.data)
      } else {
        console.log('부서목록 조회 실패')
      }
    }
    asyncDB()
  }
  // 부서목록 가져오기

  // 부서목록 JSON포맷 가져오기 
  const jsonDeptList = async () => {
    const res = await deptListDB({deptno:0}) // 0: 초기화 의미
    console.log(res.data)
    
    if (res.data) {
      setDeptList(res.data)
    } else {
      console.log('부서목록 조회 실패')
    }
  }


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

  // 부서 등록 구현 
  // 스프링 부트와 리액트 연동하기 - @RequestBody사용해서 JSON포맷으로 넘김
  const deptInsert = async () => {
    const dept = {
      deptno,
      dname,
      loc,
      filename: files.filename,
      fileurl: files.fileurl
    }
    const res = await deptInsertDB(dept)

    if (!res.data) {
      console.log("부서등록에 실패")
    }
    else {
      console.log("부서등록에 성공")
      // 성공 시 부서목록 새로고침 처리할 것
      // -> location.reload() 쓰지 말것
      // => SPA 컨벤션
      // : useEffect - 의존성 배열을 연습할 수 있다
      handleClose() // 모달창 닫기

      // 부서목로 새로고침
      navigate("/dept/1")
    }
  }

  useEffect (()=> {
    jsonDeptList()
  },[gubun]) // 의존성 배열이 빈 배열이면 최초 한번만
  // 의존성 배열에 올 수 있는 변수는 전역변수만 가능

  return (
    <>
      <BlogHeader />
      <div className='container'>
        <div className="page-header">
          <h2>부서관리</h2>
          <div className="page-header">
      <h2>부서관리&nbsp;<i className="fa-solid fa-angles-right"></i>&nbsp;<small>부서목록</small></h2>
      <hr />
      </div>      
      <div className="row">
        <div className="col-3">
          <select id="gubun" className="form-select" aria-label="분류선택">
            <option defaultValue>분류선택</option>
            <option value="deptno">부서번호</option>
            <option value="dname">부서명</option>
            <option value="loc">지역</option>
          </select>			
        </div>
        <div className="col-6">
          <input type="text" id="keyword" className="form-control" placeholder="검색어를 입력하세요" 
                  aria-label="검색어를 입력하세요" aria-describedby="btn_search" />
        </div>
        <div className="col-3">
          <Button variant='danger' id="btn_search" onClick={reactSearch}>검색</Button>
        </div>
        </div> 
        <div className='book-list'>
          <Table striped bordered hover>
            <thead>
              <tr>
                <th>#</th>
                <th>부서번호</th>
                <th>부서명</th>
                <th>지역</th>
              </tr>
            </thead>
            <tbody>
            {deptList.map(dept => (
              <DeptRow key={dept.DEPTNO} dept={dept} />
            ))}
            </tbody>
          </Table> 
          <hr />    
          <div className='booklist-footer'>
            <Button variant="warning" onClick={jsonDeptList}>
              전체조회
            </Button>&nbsp; 
            <Button variant="success" onClick={handleShow}>
              부서등록
            </Button> 
          </div>
        </div>
      </div>
      {/* ========================== [[ 도서등록 Modal ]] ========================== */}
      <Modal show={show} onHide={handleClose} animation={false}>
        <Modal.Header closeButton>
          <Modal.Title>부서등록</Modal.Title>
        </Modal.Header>
        <Modal.Body>
        <div style={{display:'flex', flexWrap:'wrap', justifyContent:'center'}}>
          <div style={{display:'flex'}}>
            <MyLabel>부서번호<span style={{color:'red'}}>{star.deptno}</span>
              <MyInput type="text" name="deptno" placeholder="Enter 부서번호" onChange={(e) => {(handleDeptno(e.target.value))}} />
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
          <div id="uploadImg">
            <img className='thumbNail' src="http://via.placeholder.com/200X250" alt="미리보기" />
          </div>
        </div>

        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>
            닫기
          </Button>
          <Button variant="primary" onClick={deptInsert}>
            저장
          </Button>
        </Modal.Footer>
      </Modal>     
      {/* ========================== [[ 부서등록 Modal ]] ========================== */}  
      </div>
    </>
  )
}

export default DeptPage
