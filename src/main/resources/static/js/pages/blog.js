document.addEventListener("DOMContentLoaded", () => {
  const deleteButtons = document.querySelectorAll("[data-blog-delete]");
  deleteButtons.forEach((button) => {
    button.addEventListener("click", () => {
      FM.confirmNavigate("게시글을 삭제하시겠습니까?", button.dataset.blogDelete);
    });
  });

  const fileInput = document.getElementById("tb_thum");
  const preview = document.getElementById("blogPreview");
  fileInput?.addEventListener("change", () => {
    const file = fileInput.files?.[0];
    if (!file || !preview) return;
    preview.src = URL.createObjectURL(file);
    preview.hidden = false;
  });
});
