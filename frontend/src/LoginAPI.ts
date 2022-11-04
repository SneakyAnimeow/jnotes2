export const login = async (username: string, password: string) => {
    const response = await fetch("/login/login", {
        method: "POST",
        credentials: "include",
        body: JSON.stringify({username, password}),
    });

    let text = await response.text();
    if (text !== "OK")
        text = JSON.parse(text).message;

    return text;
}

export const logout = async () => {
    const response = await fetch("/login/logout", {
        method: "POST",
        credentials: "include",
    });

    let text = await response.text();
    if (text !== "OK")
        text = JSON.parse(text).message;

    return text;
}

export const register = async (username: string, password: string) => {
    const response = await fetch("/login/register", {
        method: "POST",
        credentials: "include",
        body: JSON.stringify({username, password}),
    });

    let text = await response.text();
    if (text !== "OK")
        text = JSON.parse(text).message;

    return text;
}