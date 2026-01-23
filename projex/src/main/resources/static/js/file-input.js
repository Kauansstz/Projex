document.addEventListener('DOMContentLoaded', () => {
    const input = document.getElementById('fileInput');
    const progressBar = document.getElementById('progressBar');
    const fileName = document.getElementById('fileName');
    const clearBtn = document.getElementById('clearFile');
    const uploadBtn = document.getElementById('uploadBtn');
    const icon = document.getElementById('icon');

    let progressInterval;
    let isLoading = false;

    input.addEventListener('change', () => {
        if (!input.files.length || isLoading) return;
        startProgress();
    });

    clearBtn.addEventListener('click', (e) => {
        e.preventDefault();
        resetAll();
    });

    function startProgress() {
        isLoading = true;
        let progress = 0;

        // bloqueia clique durante carregamento
        uploadBtn.classList.add('pointer-events-none');

        clearBtn.classList.add('hidden');
        fileName.textContent = 'Carregando...';
        fileName.classList.remove('text-gray-600', 'text-purple-600');
        fileName.classList.add('text-white');

        icon.classList.remove('text-gray-500', 'text-purple-600');
        icon.classList.add('text-white');

        progressBar.style.width = '0%';
        progressBar.classList.remove('hidden');

        uploadBtn.classList.remove(
            'border-purple-500',
            'shadow-[0_0_0_3px_rgba(168,85,247,0.45)]'
        );

        clearInterval(progressInterval);

        progressInterval = setInterval(() => {
            progress += 2.5; // 2s total

            if (progress >= 100) {
                progress = 100;
                progressBar.style.width = '100%';
                clearInterval(progressInterval);
                showCompleted();
            } else {
                progressBar.style.width = progress + '%';
            }
        }, 50);
    }

    function showCompleted() {
        fileName.textContent = 'Concluído';

        setTimeout(() => {
            progressBar.style.width = '0%';
            progressBar.classList.add('hidden');

            fileName.textContent = input.files[0].name;
            fileName.classList.remove('text-white');
            fileName.classList.add('text-purple-600');

            icon.classList.remove('text-white');
            icon.classList.add('text-purple-600');

            uploadBtn.classList.add(
                'border-purple-500',
                'shadow-[0_0_0_3px_rgba(168,85,247,0.45)]'
            );

            clearBtn.classList.remove('hidden');
            uploadBtn.classList.remove('pointer-events-none');
            isLoading = false;
        }, 2000);
    }

    function resetAll() {
        clearInterval(progressInterval);
        isLoading = false;

        // permite selecionar o MESMO arquivo novamente
        input.value = '';

        fileName.textContent = 'Anexar certificado';
        fileName.className = 'text-gray-600 text-[18px]';

        icon.className = 'bi bi-card-image text-gray-500 text-[18px]';

        progressBar.style.width = '0%';
        progressBar.classList.add('hidden');

        uploadBtn.classList.remove(
            'border-purple-500',
            'shadow-[0_0_0_3px_rgba(168,85,247,0.45)]',
            'pointer-events-none'
        );

        clearBtn.classList.add('hidden');
    }
});
