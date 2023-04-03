package com.example.mblog1.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.mblog1.logic.RepleBoardLogic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.Gson;

@RestController
@RequestMapping("/reple/*")
public class RepleBoardController {
    Logger logger = LogManager.getLogger(RepleBoardController.class);
    @Autowired
    private RepleBoardLogic repleBoardLogic = null;
    @PostMapping("qnaInsert")
    public String qnaInsert(@RequestBody Map<String,Object> pMap) {//리액트에서 body에 {}객체리터럴로 넘겨준 정보를 Map이나 VO담을 수 있다
        logger.info("qnaInsert");//해당 메소드 호출 여부 찍어보기
        logger.info(pMap);//리액트 화면에서 넘어온 값 출력해보기
        int result = 0;
        result = repleBoardLogic.qnaInsert(pMap);
        return String.valueOf(result);
    }

    @GetMapping("imageGet")
    public Object imageGet(HttpServletRequest req, HttpServletResponse res) {
        //imageName정보는 공통코드로 제공된 QuillEditor.jsx에서 파라미터로 넘어오는 값임
        //imageUpload메소드에서는 업로드된 파일정보(파일명, 파일크기)가 리턴 됨
        String b_file = req.getParameter("imageName");//get방식으로 넘어온
        logger.info("imageGet 호출 성공===>"+b_file);//XXX.png

        //톰캣 서버의 물리적인 위치
        String filePath = "D:\\dev_spring\\intelliJ\\mblog-1\\src\\main\\webapp\\pds"; // 절대경로.
        String fname = b_file;
        logger.info("b_file: 8->euc"+b_file);

        //File은 내용까지 복제되는 것은 아니고 파일명만 객체화 해줌 클래스이다
        File file = new File(filePath,b_file.trim());

        //실제 업로드된 파일에 대한 마임타입을 출력해줌
        String mimeType = req.getServletContext().getMimeType(file.toString());
        logger.info(mimeType);//image, video, text

        if(mimeType == null){//마임타입이 널이면
            //아래 속성값으로 마임타입을 설정해줌
            //왜이렇게 하나요? 브라우저는 해석이 가능한 마임타입은 페이지 로딩 처리함
            //강제로 다운로드 처리를 위한 속성값 변경
            //브라우저에서 해석가능한 마임타입의 경우 화면에 그대로 출력이 되는 것을 방지하기 위해 추가됨
            res.setContentType("application/octet-stream");
        }

        //다운로드 되는 파일 이름 담기
        String downName = null;
        //위 File객체에서 생성된 객체에 내용을 읽기 위한 클래스 선언
        FileInputStream fis = null;
        //응답으로 나갈 정보가 웹서비스에 처리되어 하기에 사용한 객체
        ServletOutputStream sos = null;

        try{
            if(req.getHeader("user-agent").indexOf("MSIE")==-1){
                downName = new String(b_file.getBytes("UTF-8"),"8859_1");
            }else{
                downName = new String(b_file.getBytes("EUC-KR"),"8859_1");
            }

            //응답 헤더에 다운로드 될 파일명을 매핑 하기
            res.setHeader("Content-Disposition", "attachment;filename="+downName);
            //위에서 생성된 파일 문자열 객체를 가지고 파일 생성에 필요한 객체의 파라미터 넘김
            fis = new FileInputStream(file);
            sos = res.getOutputStream();
            //파일 내용을 담을 byte배열을 생성
            byte b[] = new byte[1024*10];
            int data = 0;

            while((data=(fis.read(b,0,b.length)))!=-1){
                //파일에서 읽은 내용을 가지고 실제 파일에 쓰기 처리 함
                //여기서 처리된 브라우저를 통해서 내보내진다
                sos.write(b,0,data);
            }

            //처리한 내용이 버퍼에 있는데 이것을 모두 처리 요청을 하기
            //내보내고 버퍼를 비운다 - 버퍼는 크기가 작음- 휘발성
            sos.flush();
        }catch(Exception e){
            logger.info(e.toString());
        }finally{
            try {
                if(sos != null) sos.close();
                if(fis != null) fis.close();
            } catch (Exception e2) {
                // TODO: handle exception
            }
        }

        //byte[] fileArray = boardLogic.imageDownload(imageName);
        //logger.info(fileArray.length);
        return null;
    }// end of imageGet

    @PostMapping("imageUpload")
    public Object imageUpload(MultipartHttpServletRequest mRequest,@RequestParam(value="image", required=false) MultipartFile image) {
        logger.info("imageUpload 호출 성공");
        // 사용자가 선택한 파일 이름 담기
        String filename = null;

        if(!image.isEmpty()) {
            filename = image.getOriginalFilename();

            // 이미지에 대한 업로드이므로 첨부파일 크기 계산은 하지 않음
            // 스프링프로젝트가 바라보는 물리적인 위치 정보
            String saveFolder = "D:\\dev_spring\\intelliJ\\mblog-1\\src\\main\\webapp\\pds";
            String fullPath = saveFolder+"\\"+filename;

            try {
                // File객체는 파일명을 객체화 해주는 클래스임
                // -> 생성되었다고 해서 실제 파일까지 생성되는 것이 아님
                File file = new File(fullPath);
                byte[] bytes = image.getBytes();

                // outstream반드시 생성해서 파일정보를 읽어서 쓰기를 처리해줌
                // BufferedOutputStream은 필터 클래스이지 실제 파일 쓸수 없는 객체
                // => 실제 파일 쓰기가 가능한 클래스가 FileOutputStream임
                //    : 생성자 파라미터에 파일 정보 담기
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                bos.write(bytes);   // bos : 주소번지

                // 파일쓰기와 관련 위변조 방지 위해 사용후 반드시 닫을 것
                bos.close();
            } catch (Exception e) {
                // TODO: handle exception
            }
        }

        // 리턴 값으로 선택한 이미지 파일명을 넘겨서
        // 사용자 화면에 첨부된 파일명을 열거해 주는데 사용할 것임
        String temp = filename;

        return temp;
    }
    @PostMapping("fileUpload")
    public Object fileUpload(MultipartHttpServletRequest mRequest,@RequestParam(value="file_name", required=false) MultipartFile file_name) {
        logger.info("fileUpload 호출 성공");
        //사용자가 선택한 파일 이름 담기
        String filename = null;
        if(!file_name.isEmpty()) {
            filename = file_name.getOriginalFilename();
            String saveFolder = "E:\\workspace_sts\\mblog-1\\src\\main\\webapp\\pds";
            String fullPath = saveFolder+"\\"+filename;
            try {
                //File객체는 파일명을 객체화 해주는 클래스임 - 생성되었다고 해서 실제 파일까지 생성되는 것이 아님
                File file = new File(fullPath);
                byte[] bytes = file_name.getBytes();
                //outstream반드시 생성해서 파일정보를 읽어서 쓰기를 처리해줌
                //BufferedOutputStream은 필터 클래스이지 실제 파일 쓸수 없는 객체
                //실제 파일 쓰기가 가능한 클래스가 FileOutputStream임 - 생성자 파라미터에 파일 정보 담기
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                bos.write(bytes);
                //파일쓰기와 관련 위변조 방지 위해 사용후 반드시 닫을 것
                bos.close();
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        //리턴 값으로 선택한 이미지 파일명을 넘겨서 사용자 화면에 첨부된 파일명을 열거해 주는데 사용할 것임
        String temp = filename;
        return temp;
    }

    // http://localhost:8000/reple/qnaList?content=제목
    // http://localhost:8000/reple/qnaList?content=제목&condition=작성자
    @GetMapping("qnaList")
    public String qnaList(@RequestParam Map<String,Object> pMap) {
        logger.info("qnaList호출");
        logger.info("pMap ===> " + pMap);
        List<Map<String,Object>> bList = null;
        bList = repleBoardLogic.qnaList(pMap);

        Gson g = new Gson();
        String temp = g.toJson(bList);

        return temp;
    }
}
