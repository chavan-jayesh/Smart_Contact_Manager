document.querySelector("#theme_change_button").addEventListener("click", () => {
    const html = document.documentElement;

    if (html.classList.contains("dark")) {
        html.classList.remove("dark");
        html.classList.add("light");
        document.querySelector("#theme_change_button")
                .querySelector("span")
                .textContent = "Light";
    } else {
        html.classList.remove("light");
        html.classList.add("dark");
        document.querySelector("#theme_change_button")
                .querySelector("span")
                .textContent = "Dark";
    }
});