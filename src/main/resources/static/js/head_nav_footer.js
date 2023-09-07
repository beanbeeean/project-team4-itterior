function getCurrentPageURL() {
    return window.location.pathname;
}

// 페이지가 로드될 때 실행
window.addEventListener("DOMContentLoaded", function () {
    console.log(" window.addEventListener()");

    // 현재 페이지 URL을 가져옴
    const currentPageURL = getCurrentPageURL();

    // 각 링크에 대한 URL 설정
    const boardLink = "/board";
    const productLink = "/product/";
    const youtubeLink = "/youtube";

    // 현재 페이지와 각 링크의 URL을 비교하여 스타일 변경
    if (currentPageURL === boardLink) {
        document.querySelector(".community").style.color = "rgb(255,144,144)"; // 원하는 스타일로 변경
    } else if (currentPageURL === productLink) {
        document.querySelector(".furniture").style.color = "rgb(255,144,144)"; // 원하는 스타일로 변경
    } else if (currentPageURL === youtubeLink) {
        document.querySelector(".youtube").style.color = "rgb(255,144,144)"; // 원하는 스타일로 변경
    }
});

