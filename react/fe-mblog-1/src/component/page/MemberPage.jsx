import React, { useEffect, useState } from 'react'
import { memberListDB } from '../../service/dbLogic'

const MemberPage = ({imageUploader}) => {
  console.log("MemberPage 호출")

  const [member, setMember] = useState({})

  useEffect(() => {
    const memberList = async() => {
      const res = await memberListDB(member)
      console.log(res.data)
    }

    memberList()
  },[])

  // async는 비동기 처리 시 사용한다
  const imgChange = async (event) => {
    console.log(event.target.files[0])
    // async가 붙은 함수 안에서만 await을 사용할 수 있다
    // -> 파일이 업로드 될 때까지 기다린다
    const uploaded = await imageUploader.upload(event.target.files[0])
    // public_id : 선택한 이미지의 실제 아이디가 아닌 cloudinary에 부여한느 아이디값
    // 이 값으로 실제 이미지 링크 정보가 생성된다
    // format : 선택한 파일의 확장자
    // url : 링크 이미지 URL정보
    console.log(`${uploaded.public_id} ${uploaded.format} ${uploaded.url}`)
  }

  return (
    <>
      <h3>회원관리 페이지입니다</h3>
      <input type="file" name="mimg" id="mimg" onChange={imgChange} />
    </>
  )
}

export default MemberPage
