document.addEventListener("DOMContentLoaded", () => {
  document.querySelectorAll("[data-comment-delete]").forEach((button) => {
    button.addEventListener("click", () => {
      FM.confirmNavigate("댓글을 삭제하시겠습니까?", button.dataset.commentDelete);
    });
  });
});
