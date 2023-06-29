export function setUpQuestionForm() {
  let questions = document.querySelectorAll(".response-box");
  Array.from(questions)
    .map((element) => {
      let detail = element.querySelector(".detail-response");
      if (detail != null) return detail;
    })
    .filter((element) => {
      return element != undefined;
    })
    .forEach((element) => {
      let target = element.parentElement;
      target.addEventListener("click", () => {
        if (!target.classList.contains("selected-box-response")) {
          target.classList.add("selected-box-response");
          showUrlBox(element);
        }
        removeOtherSelectedQuestion(target);
      });
    });
}

export function removeOtherSelectedQuestion(question) {
  document.querySelectorAll(".response-box").forEach((element) => {
    if (element != question) {
      element.classList.remove("selected-box-response");
      element.children[1].style.opacity = 0;
      element.children[1].innerHTML = "";
    }
  });
}

/**
 *
 * @param {HTMLElement} container
 */
export function showUrlBox(container) {
  let box = document.createElement("div");
  let url = container.getAttribute("data-url");
  box.classList.add("box-url");
  box.innerHTML = `
    <div class="url-title"> URL </div>
    <div class="url-path">
      ${url}
    </div>
  `;
  container.style.transform = "translateY(-50%) translateX(-50%)";
  container.style.opacity = 0;
  container.appendChild(box);
  setTimeout(() => {
    container.style.transform = "translateY(0%) translateX(-50%)";
    container.style.opacity = 1;
    setUpLink();
  }, 100);
}

function setUpLink() {
  console.log("atoo");
  let link = document.querySelector(".url-path");
  if (link != null) {
    link.addEventListener("click", () => {
      window.location = link.textContent.trim();
    });
  }
}
