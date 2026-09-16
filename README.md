# AI Usage Report

### 1. What tool did you use?
**Tool Name:** Gemini 3.6 Flash

### 2. What prompt did you use?
The exact primary sequence of prompts submitted to the AI assistant included:

> "Evaluate the experimental design and statistical approach for benchmarking Cyclic Sort across Java and Python implementations. The benchmark measures execution times across 4 array sizes ($N \in \{100k, 500k, 1M, 2M\}$), 3 array characteristics (`random`, `reversed`, `nearly_sorted`), and $K = 30$ independent repetitions per condition.
> 
> **Task:** 
> Formulate Welch’s two-sample $t$-test ($H_0: \mu_{\text{Python}} - \mu_{\text{Java}} \le 0$) and specify the Welch–Satterthwaite equation for degrees of freedom ($df$) to handle runtime variance heteroscedasticity between compiled (JVM) and interpreted (Python) environments.

### 3. How was your experience? (Self-Assessment)
The AI tool acted as an effective interactive thought partner and execution assistant. It helped rapidly structure the statistical pipeline, refine mathematical formulas in LaTeX, and troubleshoot data presentation issues (such as applying logarithmic scales to manage multi-order-of-magnitude execution gaps between Python and Java).

Working iteratively through each section ensured that every statistical formula, Welch–Satterthwaite calculation, Seaborn chart annotation, and Markdown section was logically validated and aligned with standard parametric benchmarking practices before inclusion in the notebook.

### 4. How would you rate your experience?
**Qualitative Assessment:** Excellent (5/5)  
**Justification:** The AI provided mathematically precise statistical code using `scipy.stats.ttest_ind(..., equal_var=False)`, formatted complex $df$ equations cleanly in LaTeX, generated publication-grade visual scripts, and accurately articulated low-level execution concepts (JVM HotSpot compilation, garbage collection overhead, memory cache locality, and dynamic dispatch).

### 5. What learning techniques did you gain from this?
* **Statistical Model Selection for Hardware Benchmarks:** Learned why Welch’s $t$-test is superior to standard $t$-test when comparing different execution runtimes due to unequal variances (heteroscedasticity) caused by JVM JIT fluctuations versus CPython bytecode execution.
* **Log-Scale Performance Visualization:** Gained practical knowledge in applying logarithmic transformations (`set_yscale("log")`) and custom major formatters (`matplotlib.ticker.FuncFormatter`) to clearly visualize asymptotic growth ($O(N)$) across widely disparate execution times.
* **Parametric Benchmarking Rigor:** Realized the importance of incorporating JIT warm-up iterations, fixing random seeds for input reproducibility, and retaining $K = 30$ independent sample runs to satisfy Central Limit Theorem (CLT) assumptions.

### 6. What is still missing?
* **Hardware-Level Metric Profiling:** The current evaluation relies on external execution wall-clock time (`System.nanoTime()` and `time.perf_counter_ns()`). Hardware counter metrics—such as CPU L1/L2 cache misses, instruction counts, and branch mispredictions—would provide deeper mechanistic insights.
* **Garbage Collection (GC) Monitoring:** Detailed GC logging was not explicitly captured during Java runs, which could account for minor variance spikes observed in high-$N$ conditions.
* **Extended Runtime Engines:** Future comparisons could expand beyond CPython and OpenJDK to evaluate alternative execution runtimes, such as PyPy (JIT for Python) or GraalVM, to isolate language syntax overhead from runtime optimization capabilities.
