function getCurrentPageURL() {
    return window.location.pathname;
}

// 페이지가 로드될 때 실행
window.addEventListener("DOMContentLoaded", function () {
    console.log(" window.addEventListener()");

    // 현재 페이지 URL을 가져옴
    const currentPageURL = getCurrentPageURL();

    // 각 링크에 대한 URL 설정
    const userLink = "/admin/user_list";
    const adminLink = "/admin/admin_list";
    const youtubeLink = "/admin/youtube_list";
    const ytChannelLink = "/admin/youtube_channel_list";
    const boardLink = "/admin/board_list";
    const productLink = "/admin/product_list";
    const modifyLink = "/admin/admin_modify_form";

    switch (currentPageURL){
        case userLink:
            document.querySelector(".admin_u").style.color = "rgb(241, 73, 11)"; // 원하는 스타일로 변경
            break;
        case adminLink:
            document.querySelector(".admin_a").style.color = "rgb(241, 73, 11)"; // 원하는 스타일로 변경
            break;
        case youtubeLink:
            document.querySelector(".admin_y").style.color = "rgb(241, 73, 11)"; // 원하는 스타일로 변경
            break;
        case ytChannelLink:
            document.querySelector(".admin_yc").style.color = "rgb(241, 73, 11)"; // 원하는 스타일로 변경
            break;
        case boardLink:
            document.querySelector(".admin_b").style.color = "rgb(241, 73, 11)"; // 원하는 스타일로 변경
            break;
        case productLink:
            document.querySelector(".admin_p").style.color = "rgb(241, 73, 11)"; // 원하는 스타일로 변경
            break;
        case modifyLink:
            document.querySelector(".admin_m").style.color = "rgb(241, 73, 11)"; // 원하는 스타일로 변경
            break;
    }

});

