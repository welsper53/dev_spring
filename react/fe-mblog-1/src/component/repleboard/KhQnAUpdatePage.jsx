import React from 'react'
import { useRef } from 'react';
import { useEffect } from 'react';
import { useCallback } from 'react';
import { useState } from 'react';
import { Form } from 'react-bootstrap';
import { json, useNavigate, useParams } from 'react-router-dom';
import { qnaDetailDB, qnaUpdateDB } from '../../service/dbLogic';
import { BButton, ContainerDiv, FormDiv, HeaderDiv } from '../styles/FormStyle';
import KhMyFilter from './KhMyFilter';
import QuillEditor from './QuillEditor';

const KhQnAUpdatePage = ({authLogic}) => {
  // 해시값으로 가져오기
  // 쿼리스트링 가져오기
  // props로 가져오기
  const { bno } = useParams()   // 해시값
  console.log(bno);

  const[title, setTitle]= useState('');
  const[content, setContent]= useState('');
  const[files, setFiles]= useState([]);
  const[secret, setSecret]= useState(false);
  const[tTitle, setTTitle]= useState('일반');
  const[types]= useState(['일반','결제','양도','회원','수업']);
  const quillRef = useRef();

  const navigate = useNavigate();

  useEffect(() => {
    //한 건 가져오기

    const qnaDetail = async () => {
      const board = {
        qna_bno: bno,
      }

      const res = await qnaDetailDB(board);
      console.log(res.data)

      const temp = JSON.stringify(res.data);  // 문자열 전환
      const jsonDoc = JSON.parse(temp);       // json형식의 배열 접근 처리

      setTitle(jsonDoc[0].QNA_TITLE)
      setContent(jsonDoc[0].QNA_CONTENT)
      setFiles(jsonDoc[0].QNA_FILES)
      // 여기서 parse는 "true"를 boolean 타입의 true로 변경
      setSecret(JSON.parse(jsonDoc[0].QNA_SECRET))
      setTTitle(jsonDoc[0].QNA_TYPE)

      // 작성자가 아닌데 수정해도 되나?
      if (jsonDoc[0].MEM_NO !== sessionStorage.getItem("no")) {  
        // 글 회원번호와 로그인한 no가 달라? => 다른 회원
        return () => {
          console.log("넌 작성자가 아니잖아")
        }
      } 
    }

    qnaDetail()
  },[bno]);
  

  const handleContent = useCallback((value) => {
    console.log(value);
    setContent(value);
  },[]);


  const handleFiles = useCallback((value) => {
    setFiles([...files, value]);
  },[files]);


  const handleTitle = useCallback((e) => {
    setTitle(e);
  },[]); 

  const handleTTitle = useCallback((e) => {
    setTTitle(e);
  },[]);

  const boardUpdate = async() => {
    if (title.trim() === "" || content.trim() === "") {
      return console.log("게시글이 수정(작성)되지 않았다")
    }

    const board = {
      qna_bno: bno,
      qna_title: title,             // useState 훅이다
      qna_content: content,
      qna_secret: (secret ? "true" : "false"),
      qna_type: tTitle,
    }

    const res = await qnaUpdateDB(board);

    if (!res.data) {
      return console.log("게시판 수정 실패했습니다.")
    }

    navigate("/qna/list?page=1")
  }


  return (
    <>
      <ContainerDiv>
        <HeaderDiv>
          <h3 style={{marginLeft:"10px"}}>QNA 글수정</h3>
        </HeaderDiv>
        <FormDiv>
          <div style={{width:"100%", maxWidth:"2000px"}}>
            <div style={{display: 'flex', justifyContent: 'space-between', marginBottom:'10px'}}>
              <h2>제목</h2> 
              <div style={{display: 'flex'}}>
                <div style={{display: 'flex', flexDirection: 'column', marginRight:'10px', alignItems: 'center'}}>
                  <span style={{fontSize: '14px'}}>비밀글</span> 
                  <Form.Check type="switch" id="custom-switch" checked={secret===true?true:false} readOnly
                    style={{paddingLeft: '46px'}} onClick={()=>{setSecret(!secret)}}/>
                </div>
                <KhMyFilter types={types} id={"qna_type"} title={tTitle} handleTitle={handleTTitle}></KhMyFilter>
                <BButton style={{marginLeft: '10px'}} onClick={()=>{boardUpdate()}}>글수정</BButton>
              </div>
            </div>
            <input id="dataset-title" type="text" placeholder="제목을 입력하세요." defaultValue={title}
              style={{width:"100%",height:'40px' , border:'1px solid lightGray'}} onChange={(e)=>{handleTitle(e.target.value)}}/>
            <hr />
            <h3 style={{textAlign:"left", marginBottom:'10px'}}>상세내용</h3>
            <QuillEditor value={content} handleContent={handleContent} quillRef={quillRef} files={files} handleFiles={handleFiles}/>
          </div>
        </FormDiv>
      </ContainerDiv>
    </>
  );
};

export default KhQnAUpdatePage