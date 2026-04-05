document.addEventListener("DOMContentLoaded", async () => {
  const commentList = document.getElementById("blogcmt");
  const commentForm = document.getElementById("blogcmtform");
  const postNo = document.getElementById("blogPostNo")?.value;
  if (!postNo || !commentList || !commentForm) return;

  const commentResponse = await fetch("/cmt/blogcmt", { headers: { Referer: window.location.href } });
  commentList.innerHTML = await commentResponse.text();

  const formResponse = await fetch(`/cmt/blogcmtform?c_tbset=${postNo}&c_tbno=`);
  commentForm.innerHTML = await formResponse.text();
});
