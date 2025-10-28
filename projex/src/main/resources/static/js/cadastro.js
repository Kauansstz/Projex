document.addEventListener('DOMContentLoaded', function () {
  const telefone = document.getElementById('telefone');
  if (telefone) {
    telefone.addEventListener('input', function (e) {
      let v = e.target.value.replace(/\D/g, '');
      if (v.length > 11) v = v.slice(0, 11);
      v = v.replace(/^(\d{2})(\d)/g, '($1) $2');
      v = v.replace(/(\d{5})(\d{4})$/, '$1-$2');
      e.target.value = v;
    });
  }
});


document.addEventListener('DOMContentLoaded', () => {
  const cpfInput = document.getElementById('cpf');
  if (!cpfInput) return; // evita erro se o elemento não existir

  cpfInput.addEventListener('input', function (e) {
      let v = e.target.value.replace(/\D/g, '');
      if (v.length > 11) v = v.slice(0, 11);

      if (v.length > 9) {
          v = v.replace(/^(\d{3})(\d{3})(\d{3})(\d{2})$/, "$1.$2.$3-$4");
      } else if (v.length > 6) {
          v = v.replace(/^(\d{3})(\d{3})(\d{0,3})$/, "$1.$2.$3");
      } else if (v.length > 3) {
          v = v.replace(/^(\d{3})(\d{0,3})$/, "$1.$2");
      }

      e.target.value = v;
  });
});


document.addEventListener('DOMContentLoaded', () => {
  const senha = document.getElementById('senha');
  const confirma = document.getElementById('confirmaSenha');

  if (!senha || !confirma) return;

  confirma.addEventListener('input', () => {
    // Remove classes de cores antigas
    confirma.classList.remove('border-red-500', 'border-green-500');

    // Adiciona cor correta
    if (confirma.value === senha.value && confirma.value !== '') {
      confirma.classList.add('border-green-500');
    } else if (confirma.value !== senha.value) {
      confirma.classList.add('border-red-500');
    }
  });
});
