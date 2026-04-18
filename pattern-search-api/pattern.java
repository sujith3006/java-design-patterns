package com.example.patternsearch;

public class Pattern {
    private String name;
    private String description;

    public Pattern(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
}

PatternController.java
  package com.example.patternsearch;

import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/patterns")
public class PatternController {

    private List<Pattern> patterns = List.of(
        new Pattern("Strategy", "Encapsulate algorithms"),
        new Pattern("Observer", "Subscribe/notify model"),
        new Pattern("Factory Method", "Create objects without specifying class"),
        new Pattern("Builder", "Stepwise object construction"),
        new Pattern("Decorator", "Add behavior dynamically")
    );

    @GetMapping("/search")
    public List<Pattern> searchPatterns(@RequestParam String query) {
        return patterns.stream()
                .filter(p -> p.getName().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }
}
index.html:
<input type="text" id="patternSearch" placeholder="Search patterns..." />

<ul id="patternList">
  <!-- dynamic content here -->
</ul>

<script>
async function fetchPatterns(query) {
  const res = await fetch(`http://localhost:8080/api/patterns/search?query=${query}`);
  const data = await res.json();
  const list = document.getElementById("patternList");
  list.innerHTML = "";
  data.forEach(p => {
    const li = document.createElement("li");
    li.textContent = p.name + " - " + p.description;
    list.appendChild(li);
  });
}

document.getElementById("patternSearch").addEventListener("input", (e) => {
  fetchPatterns(e.target.value);
});
</script>
