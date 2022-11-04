import {useEffect} from "react";
import {logout} from "../LoginAPI"
import Cookies from "js-cookie";
import {client} from "../index";

export default function Logout() {
    useEffect(() => {
        logout().then(() => {
            Cookies.remove("token")
            client.resetStore();
        });
    }, []);

    return (
        <p>Logging out...</p>
    );
}