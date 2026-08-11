use std::process::{Command, exit};
use std::thread;
use std::time::Duration;
use sysinfo::{Disks, System};

// Estruturas da API nativa do Windows (kernel32.dll)
#[repr(C)]
#[derive(Default, Copy, Clone)]
struct FILETIME {
    dw_low_date_time: u32,
    dw_high_date_time: u32,
}

 unsafe extern "system" {
    fn GetSystemTimes(
        lp_idle_time: *mut FILETIME,
        lp_kernel_time: *mut FILETIME,
        lp_user_time: *mut FILETIME,
    ) -> i32;
}

fn filetime_to_u64(ft: FILETIME) -> u64 {
    ((ft.dw_high_date_time as u64) << 32) | (ft.dw_low_date_time as u64)
}

// Leitura direta do uso de CPU via API do Windows
fn obter_uso_cpu_windows() -> f32 {
    let mut idle1 = FILETIME::default();
    let mut kernel1 = FILETIME::default();
    let mut user1 = FILETIME::default();

    unsafe { GetSystemTimes(&mut idle1, &mut kernel1, &mut user1); }

    // Amostragem de 1 segundo para bater com a média móvel do Windows
    thread::sleep(Duration::from_secs(1));

    let mut idle2 = FILETIME::default();
    let mut kernel2 = FILETIME::default();
    let mut user2 = FILETIME::default();

    unsafe { GetSystemTimes(&mut idle2, &mut kernel2, &mut user2); }

    let idle = filetime_to_u64(idle2).saturating_sub(filetime_to_u64(idle1));
    let kernel = filetime_to_u64(kernel2).saturating_sub(filetime_to_u64(kernel1));
    let user = filetime_to_u64(user2).saturating_sub(filetime_to_u64(user1));

    let total = kernel + user;
    if total == 0 {
        return 0.0;
    }

    let ocupado = total.saturating_sub(idle);
    (ocupado as f32 / total as f32) * 100.0
}

fn clear_terminal() {
    if cfg!(target_os = "windows") {
        Command::new("cmd")
            .args(["/C", "cls"])
            .status()
            .unwrap();
    } else {
        Command::new("clear")
            .status()
            .unwrap();
    }
}

fn main() {
    clear_terminal();

    // 1. Obter CPU diretamente do Windows
    let cpu_usage = obter_uso_cpu_windows();

    // 2. Obter Memória RAM usando sysinfo
    let mut sys = System::new_all();
    sys.refresh_memory();
    
    let total_ram = sys.total_memory() as f64;
    let used_ram = sys.used_memory() as f64;
    let ram_percentage = (used_ram / total_ram) * 100.0;

    let total_ram_gb = total_ram / 1024.0 / 1024.0 / 1024.0;
    let used_ram_gb = used_ram / 1024.0 / 1024.0 / 1024.0;

    println!("=== RECURSOS DE PROCESSAMENTO E MEMÓRIA ===");
    println!("Uso da CPU: {:.2}%", cpu_usage);
    println!(
        "Uso da Memória RAM: {:.2} GB / {:.2} GB ({:.2}%)",
        used_ram_gb, total_ram_gb, ram_percentage
    );
    println!("-------------------------------------------");

    // 3. Obter Armazenamento usando sysinfo
    let disks = Disks::new_with_refreshed_list();
    let mut maior_uso_disco = 0.0;

    println!("=== RELATÓRIO DE ARMAZENAMENTO ===");
    for disk in &disks {
        let total_space_gb = disk.total_space() as f64 / 1024.0 / 1024.0 / 1024.0;
        let available_space_gb = disk.available_space() as f64 / 1024.0 / 1024.0 / 1024.0;
        let used_space_gb = total_space_gb - available_space_gb;
        let disk_percentage = (used_space_gb / total_space_gb) * 100.0;

        if disk_percentage > maior_uso_disco {
            maior_uso_disco = disk_percentage;
        }

        println!(
            "Ponto de Montagem {:?}: Capacidade Total = {:.2} GB | Espaço Livre = {:.2} GB ({:.2}% usado)",
            disk.mount_point(),
            total_space_gb,
            available_space_gb,
            disk_percentage
        );
    }
    println!("----------------------------------");

    // 4. Verificação de Limites Críticos
    let mut recurso_critico = false;
    let mut detalhes_alerta = Vec::new();

    if cpu_usage >= 90.0 {
        recurso_critico = true;
        detalhes_alerta.push(format!("CPU ({:.2}%)", cpu_usage));
    }

    if ram_percentage >= 90.0 {
        recurso_critico = true;
        detalhes_alerta.push(format!("RAM ({:.2}%)", ram_percentage));
    }

    if maior_uso_disco >= 90.0 {
        recurso_critico = true;
        detalhes_alerta.push(format!("Disco ({:.2}%)", maior_uso_disco));
    }

    if recurso_critico {
        eprintln!(
            "\n[ERRO CRÍTICO] A capacidade do sistema está alta! Excedido em: {}",
            detalhes_alerta.join(", ")
        );
        eprintln!("[SEGURANÇA] Por motivos de segurança, a aplicação irá reiniciar.\n");
        exit(1);
    }

    println!("\n[Status] Todos os recursos (CPU, RAM e Disco) estão operando em níveis seguros.");
}