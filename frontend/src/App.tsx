import React, {useState} from 'react';
import {HashRouter as Router, Route, Routes as Switch} from "react-router-dom";
import 'bootstrap/dist/css/bootstrap.min.css';

import './App.css';
import Login from "./pages/Login";
import Panel from "./pages/Panel";
import Home from "./pages/Home";
import Cookies from "js-cookie";
import {Button, Container, Nav, Navbar} from "react-bootstrap";
import Logout from "./pages/Logout";

function App() {
    const [token, setToken] = useState<string | undefined>(undefined);

    setInterval(() => {
        setToken(Cookies.get("token"));
    }, 100);

    return (
        <>
            <Navbar bg="dark" variant="dark">
                <Container>
                    <Navbar.Brand href="/">
                        <img
                            alt=""
                            src="/logo.svg"
                            width="30"
                            height="30"
                            className="d-inline-block align-top"
                        />{' '}
                        JNotes{" "}
                    </Navbar.Brand>
                    <Nav className="me-auto">
                        {token && <Nav.Link href="/#/panel">
                            Panel{" "}
                        </Nav.Link>}
                    </Nav>
                    {token && <Button variant="outline-danger" href="/#/logout">
                        Logout{" "}
                    </Button>}
                    {!token && <Button variant="outline-success" href="/#/login">
                        Login{" "}
                    </Button>}
                </Container>
            </Navbar>
            <Router>
                <Switch>
                    {!token && <Route path="/login" element={<Login/>}/>}
                    {token && <Route path="/panel" element={<Panel/>}/>}
                    {token && <Route path="/logout" element={<Logout/>}/>}
                    <Route path="*" element={<Home/>}/>
                </Switch>
            </Router>
        </>
    );
}

export default App;
