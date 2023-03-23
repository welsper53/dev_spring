import React from 'react'
import { Navbar } from 'react-bootstrap'

const BlogFooter = () => {
  return (
    <>
      <Navbar fixed="bottom"className="navbar navbar-expand-sm bg-light justify-content-center" bg="dark" style={{ color: "white" }} >
        터짐블로그 Copyright&copy;2023
      </Navbar>
    </>
  )
}

export default BlogFooter
