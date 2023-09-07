function getCurrentPageURL() {
    return window.location.pathname;
}

// 페이지가 로드될 때 실행
window.addEventListener("DOMContentLoaded", function () {
    console.log(" window.addEventListener()");

    // 현재 페이지 URL을 가져옴
    const currentPageURL = getCurrentPageURL();

    // 각 링크에 대한 URL 설정
    const productLink = "/user/user_list_product_list";
    const uBoardLink = "/user/user_board_list";
    const youtubeLink = "/user/user_list_youtube_list";
    const boardLink = "/user/user_list_board_list";
    const modifyLink = "/user/user_modify_form";

    switch (currentPageURL){
        case youtubeLink:
            document.querySelector(".user_y").style.color = "rgb(241, 73, 11)"; // 원하는 스타일로 변경
            break;
        case boardLink:
            document.querySelector(".user_b").style.color = "rgb(241, 73, 11)"; // 원하는 스타일로 변경
            break;
        case uBoardLink:
            document.querySelector(".user_ub").style.color = "rgb(241, 73, 11)"; // 원하는 스타일로 변경
            break;
        case productLink:
            document.querySelector(".user_p").style.color = "rgb(241, 73, 11)"; // 원하는 스타일로 변경
            break;
        case modifyLink:
            document.querySelector(".user_m").style.color = "rgb(241, 73, 11)"; // 원하는 스타일로 변경
            break;
    }

});

