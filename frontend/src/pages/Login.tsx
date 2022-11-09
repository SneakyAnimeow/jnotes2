import {useState} from "react";
import {login, register} from "../LoginAPI"
import {client} from "../index";
import {Button, FloatingLabel, Form, Stack} from "react-bootstrap";

import "./Login.css";

export default function Login() {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    const handleLoginSubmit = async (e: any) => {
        e.preventDefault();

        await client.resetStore();

        let result: String = await login(username, password);

        if (result !== "OK") {
            alert(result);
            return;
        }

        window.location.href = "/#/panel";
    }

    const handleRegisterSubmit = async (e: any) => {
        e.preventDefault();

        await client.resetStore();

        let result: String = await register(username, password);

        if (result !== "OK") {
            alert(result);
            return;
        }

        window.location.href = "/#/panel";
    }

    return (
        <>
            <Form>
                <div className="details-container">
                    <Form.Group className="mb-3" controlId="formBasicEmail">
                        <FloatingLabel controlId="floatingInput" label="username" children={
                            <Form.Control
                                placeholder="username"
                                aria-label="username"
                                aria-describedby="username"
                                value={username}
                                onChange={(e) => setUsername(e.target.value)}
                            />
                        }/>
                    </Form.Group>
                    <Form.Group className="mb-3" controlId="formBasicPassword">
                        <FloatingLabel controlId="floatingInput" label="password" children={
                            <Form.Control
                                placeholder="password"
                                aria-label="password"
                                aria-describedby="password"
                                type="password"
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                            />
                        }/>
                    </Form.Group>
                </div>
                <Stack className="col-md-5 mx-auto">
                    <Button variant="primary" onClick={handleLoginSubmit}>Login</Button>
                    <Button variant="info" onClick={handleRegisterSubmit}>Register</Button>
                </Stack>
            </Form>
        </>
    );
}