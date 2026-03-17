document.addEventListener("DOMContentLoaded", () => {

  const select = document.getElementById("status");

  function atualizarBorda() {
    // remove todas as cores possíveis
    select.classList.remove(
      "border-green-500",
      "border-red-500",
      "border-yellow-500",
      "border-gray-100"
    );

    if (select.value === "CONCLUIDO") {
      select.classList.add("border-green-500");
    } else if (select.value === "CANCELADO") {
      select.classList.add("border-red-500");
    } else if (select.value === "EM_ANDAMENTO") {
      select.classList.add("border-yellow-500");
    } else {
      select.classList.add("border-gray-100");
    }
  } // ✅ FECHOU A FUNÇÃO AQUI

  // executa ao carregar
  atualizarBorda();

  // executa ao trocar
  select.addEventListener("change", atualizarBorda);

});