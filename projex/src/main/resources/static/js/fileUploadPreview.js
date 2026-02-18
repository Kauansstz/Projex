(function () {

    function initFileUploadPreview(config) {

        const fileInput = document.getElementById(config.inputId);
        if (!fileInput) return;

        const fileName = document.getElementById(config.fileNameId);
        const progressBar = document.getElementById(config.progressBarId);
        const previewContainer = document.getElementById(config.previewContainerId);
        const imagePreview = document.getElementById(config.imagePreviewId);
        const pdfPreview = document.getElementById(config.pdfPreviewId);
        const clearFile = document.getElementById(config.clearButtonId);
        // ID do botão de download (certifique-se de passar isso no config ou usar o padrão)
        const downloadBtn = document.getElementById(config.downloadButtonId || "downloadBtn");

        /* =========================
            PREVIEW AUTOMÁTICO (EDIÇÃO)
        ========================== */
        function activateExistingPreview() {
            const imageSrc = imagePreview?.getAttribute("src");
            const pdfSrc = pdfPreview?.getAttribute("src");
            
            const hasImage = imageSrc && imageSrc !== "" && imageSrc !== "/uploads/";
            const hasPdf = pdfSrc && pdfSrc !== "" && pdfSrc !== "/uploads/";

            if (hasImage || hasPdf) {
                previewContainer?.classList.remove("hidden");
                if (downloadBtn) downloadBtn.classList.remove("hidden");

                if (hasImage) {
                    imagePreview.classList.remove("hidden");
                    imagePreview.style.cursor = "zoom-in";
                    enableImageZoom(imagePreview);
                    if (fileName && imageSrc.includes("_")) {
                        fileName.textContent = imageSrc.split('_').pop();
                    }
                }

                if (hasPdf) {
                    pdfPreview.classList.remove("hidden");
                    if (fileName && pdfSrc.includes("_")) {
                        fileName.textContent = pdfSrc.split('_').pop();
                    }
                }
            }
        }

        activateExistingPreview();

        /* =========================
            NOVO UPLOAD
        ========================== */
        fileInput.addEventListener("change", function () {
            const file = this.files[0];
            if (!file) return;

            if (fileName) fileName.textContent = file.name;
            if (progressBar) {
                progressBar.classList.remove("hidden");
                progressBar.style.width = "0%";
            }
            if (clearFile) clearFile.classList.remove("hidden");

            let progress = 0;
            const interval = setInterval(() => {
                progress += 10;
                if (progressBar) progressBar.style.width = progress + "%";

                if (progress >= 100) {
                    clearInterval(interval);
                    showPreview(file);
                }
            }, 60);
        });

        function showPreview(file) {
            const fileURL = URL.createObjectURL(file);
            previewContainer?.classList.remove("hidden");

            // Atualiza o botão de download para o novo arquivo
            if (downloadBtn) {
                downloadBtn.href = fileURL;
                downloadBtn.download = file.name;
                downloadBtn.classList.remove("hidden");
            }

            if (file.type.startsWith("image/")) {
                const reader = new FileReader();
                reader.onload = function (e) {
                    if (imagePreview) {
                        imagePreview.src = e.target.result;
                        imagePreview.classList.remove("hidden");
                        imagePreview.style.cursor = "zoom-in";
                        enableImageZoom(imagePreview);
                    }
                    pdfPreview?.classList.add("hidden");
                };
                reader.readAsDataURL(file);

            } else if (file.type === "application/pdf") {
                if (pdfPreview) {
                    pdfPreview.src = fileURL;
                    pdfPreview.classList.remove("hidden");
                }
                imagePreview?.classList.add("hidden");
            }
        }

        /* =========================
            LIMPAR ARQUIVO
        ========================== */
        if (clearFile) {
            clearFile.addEventListener("click", function () {
                fileInput.value = "";
                if (fileName) fileName.textContent = config.defaultText || "Anexar certificado";
                
                if (progressBar) {
                    progressBar.style.width = "0%";
                    progressBar.classList.add("hidden");
                }

                previewContainer?.classList.add("hidden");
                imagePreview?.classList.add("hidden");
                pdfPreview?.classList.add("hidden");
                if (downloadBtn) downloadBtn.classList.add("hidden");
                clearFile.classList.add("hidden");
            });
        }
    }

    /* =========================
        MODAL ZOOM GLOBAL
    ========================== */
    function enableImageZoom(imageElement) {
        let modal = document.getElementById("imageModal");
        if (!modal) {
            modal = document.createElement("div");
            modal.id = "imageModal";
            modal.className = "fixed inset-0 bg-black/80 hidden items-center justify-center z-50";
            modal.innerHTML = `
                <button id="closeModal" class="absolute top-6 right-6 text-white text-3xl hover:text-gray-300 transition">&times;</button>
                <img id="modalImage" class="max-h-[90vh] max-w-[90vw] rounded-lg shadow-2xl transition duration-300">
            `;
            document.body.appendChild(modal);
        }

        const modalImage = document.getElementById("modalImage");
        const closeModal = document.getElementById("closeModal");

        imageElement.onclick = function () {
            modal.classList.remove("hidden");
            modal.classList.add("flex");
            modalImage.src = imageElement.src;
        };

        const close = () => {
            modal.classList.add("hidden");
            modal.classList.remove("flex");
            modalImage.src = "";
        };

        closeModal.onclick = close;
        modal.onclick = (e) => { if (e.target === modal) close(); };
    }

    document.addEventListener("DOMContentLoaded", function () {
        if (window.fileUploadConfigs) {
            window.fileUploadConfigs.forEach(config => {
                initFileUploadPreview(config);
            });
        }
    });

})();