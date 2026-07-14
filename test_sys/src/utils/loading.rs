use std::thread;
use indicatif::{ProgressBar, ProgressStyle};
use std::time::Duration;

pub fn loading(){
    let pb = ProgressBar::new(100);
    pb.set_style(
        ProgressStyle::with_template(
            "[{elapsed_precise}] [{bar:40.white/black}] {percent}%"
        ).unwrap()
        .progress_chars("██ "),
    );

    println!("Carregando...");

    for _ in 0..100{
        pb.inc(1);
        thread::sleep(Duration::from_millis(50));
    }

    pb.finish_with_message("Pronto!");
}