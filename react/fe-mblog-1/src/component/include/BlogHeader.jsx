import React from 'react'
import "bootstrap/dist/css/bootstrap.min.css";
import { Container, Nav, Navbar } from 'react-bootstrap'
import { Link } from 'react-router-dom';

const BlogHeader = () => {
  return (
    <>
      <Navbar bg="dark" variant="dark">
        <Container>
            <Link to="/board">게시판</Link>
          <Link to="/" >TerrGYM</Link>
          <Nav className="me-auto">
            <Link to="/home" className='nav-link'>Home</Link>
            <Link to="/dept" className='nav-link'>부서관리</Link>
            <Link to="/board" className='nav-link'>게시판</Link>
          </Nav>
        </Container>
      </Navbar>
    </>
  )
}

export default BlogHeader
