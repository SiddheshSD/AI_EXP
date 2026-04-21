(function () {
  'use strict';

  // ── 1. Theme Toggle (dark is default) ────────
  const toggle = document.getElementById('theme-toggle');
  const stored = localStorage.getItem('theme');

  // Apply stored preference; if nothing stored, dark stays (set in HTML)
  if (stored === 'light') {
    document.documentElement.removeAttribute('data-theme');
  }

  toggle.addEventListener('click', () => {
    const isDark = document.documentElement.getAttribute('data-theme') === 'dark';
    if (isDark) {
      document.documentElement.removeAttribute('data-theme');
      localStorage.setItem('theme', 'light');
    } else {
      document.documentElement.setAttribute('data-theme', 'dark');
      localStorage.setItem('theme', 'dark');
    }
  });

  // ── 2. Copy to Clipboard ─────────────────────
  document.querySelectorAll('.copy-btn').forEach(btn => {
    btn.addEventListener('click', async () => {
      const codeEl = document.getElementById(btn.dataset.code);
      if (!codeEl) return;
      const raw = codeEl.textContent;

      try {
        await navigator.clipboard.writeText(raw);
      } catch {
        const ta = document.createElement('textarea');
        ta.value = raw;
        ta.style.cssText = 'position:fixed;opacity:0';
        document.body.appendChild(ta);
        ta.select();
        document.execCommand('copy');
        document.body.removeChild(ta);
      }

      const label = btn.querySelector('.copy-label');
      btn.classList.add('copied');
      label.textContent = 'Copied!';
      setTimeout(() => {
        btn.classList.remove('copied');
        label.textContent = 'Copy';
      }, 2000);
    });
  });

  // ── 3. Multi-Language Syntax Highlighting ─────

  function esc(s) {
    return s.replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;');
  }

  // ─── Python Highlighter ───
  const PY_KW = new Set([
    'False','None','True','and','as','assert','async','await','break',
    'class','continue','def','del','elif','else','except','finally',
    'for','from','global','if','import','in','is','lambda','nonlocal',
    'not','or','pass','raise','return','try','while','with','yield',
  ]);
  const PY_BI = new Set([
    'print','range','len','int','float','str','list','dict','set',
    'tuple','type','input','open','sorted','map','filter','zip',
    'enumerate','min','max','sum','abs','round','super','isinstance',
    'hasattr','getattr','setattr','any','all','iter','next','eval',
  ]);

  function highlightPython(code) {
    return code.split('\n').map(line => {
      const trimmed = line.trimStart();
      if (trimmed.startsWith('#')) {
        return ' '.repeat(line.length - trimmed.length) + '<span class="tk-cmt">' + esc(trimmed) + '</span>';
      }
      let r = '', i = 0;
      while (i < line.length) {
        if (line[i] === '#') { r += '<span class="tk-cmt">' + esc(line.slice(i)) + '</span>'; break; }
        if (line[i] === '"' || line[i] === "'") {
          const q = line[i]; let e = i + 1;
          if (line.slice(i, i+3) === q+q+q) {
            e = line.indexOf(q+q+q, i+3); e = e===-1 ? line.length : e+3;
          } else {
            while (e < line.length) { if (line[e]==='\\'){e+=2;continue;} if (line[e]===q){e++;break;} e++; }
          }
          r += '<span class="tk-str">' + esc(line.slice(i,e)) + '</span>'; i=e; continue;
        }
        if (/[0-9]/.test(line[i]) && (i===0 || /[\s(,\[{:=<>+\-*/%]/.test(line[i-1]))) {
          let e=i; while(e<line.length && /[0-9.eExXoObB_a-fA-F]/.test(line[e]))e++;
          r += '<span class="tk-num">' + esc(line.slice(i,e)) + '</span>'; i=e; continue;
        }
        if (/[a-zA-Z_]/.test(line[i])) {
          let e=i; while(e<line.length && /[a-zA-Z0-9_]/.test(line[e]))e++;
          const w = line.slice(i,e);
          if (PY_KW.has(w)) r += '<span class="tk-kw">' + esc(w) + '</span>';
          else if (PY_BI.has(w)) r += '<span class="tk-bi">' + esc(w) + '</span>';
          else if (e<line.length && line[e]==='(') r += '<span class="tk-fn">' + esc(w) + '</span>';
          else r += esc(w);
          i=e; continue;
        }
        if ('=<>!+-*/%&|^~'.includes(line[i])) { r += '<span class="tk-op">' + esc(line[i]) + '</span>'; i++; continue; }
        r += esc(line[i]); i++;
      }
      return r;
    }).join('\n');
  }

  // ─── Java Highlighter ───
  const JAVA_KW = new Set([
    'abstract','assert','boolean','break','byte','case','catch','char','class',
    'const','continue','default','do','double','else','enum','extends','final',
    'finally','float','for','goto','if','implements','import','instanceof','int',
    'interface','long','native','new','package','private','protected','public',
    'return','short','static','strictfp','super','switch','synchronized','this',
    'throw','throws','transient','try','void','volatile','while',
  ]);
  const JAVA_TYPES = new Set([
    'String','Integer','Boolean','Long','Double','Float','Character','Byte','Short',
    'Object','System','Math','Exception','IOException','File','BufferedReader',
    'InputStreamReader','FileReader','StringBuffer','Pattern','Matcher',
    'ArrayList','HashMap','HashSet','List','Map','Set','Collections',
  ]);

  function highlightJava(code) {
    return code.split('\n').map(line => {
      const trimmed = line.trimStart();
      // Full-line comments
      if (trimmed.startsWith('//')) {
        return ' '.repeat(line.length - trimmed.length) + '<span class="tk-cmt">' + esc(trimmed) + '</span>';
      }
      // Block comment lines (simple: whole line is part of block comment)
      if (trimmed.startsWith('/*') || trimmed.startsWith('*') || trimmed.startsWith('*/')) {
        return ' '.repeat(line.length - trimmed.length) + '<span class="tk-cmt">' + esc(trimmed) + '</span>';
      }

      let r = '', i = 0;
      while (i < line.length) {
        // Inline comment
        if (line[i] === '/' && i+1 < line.length && line[i+1] === '/') {
          r += '<span class="tk-cmt">' + esc(line.slice(i)) + '</span>'; break;
        }
        // Strings
        if (line[i] === '"') {
          let e = i + 1;
          while (e < line.length) { if (line[e]==='\\'){e+=2;continue;} if (line[e]==='"'){e++;break;} e++; }
          r += '<span class="tk-str">' + esc(line.slice(i,e)) + '</span>'; i=e; continue;
        }
        if (line[i] === "'") {
          let e = i + 1;
          while (e < line.length) { if (line[e]==='\\'){e+=2;continue;} if (line[e]==="'"){e++;break;} e++; }
          r += '<span class="tk-str">' + esc(line.slice(i,e)) + '</span>'; i=e; continue;
        }
        // Numbers
        if (/[0-9]/.test(line[i]) && (i===0 || /[\s(,\[{:=<>+\-*/%;&|!]/.test(line[i-1]))) {
          let e=i; while(e<line.length && /[0-9.xXa-fA-FLl_]/.test(line[e]))e++;
          r += '<span class="tk-num">' + esc(line.slice(i,e)) + '</span>'; i=e; continue;
        }
        // Identifiers/keywords
        if (/[a-zA-Z_$]/.test(line[i])) {
          let e=i; while(e<line.length && /[a-zA-Z0-9_$]/.test(line[e]))e++;
          const w = line.slice(i,e);
          if (JAVA_KW.has(w)) r += '<span class="tk-kw">' + esc(w) + '</span>';
          else if (JAVA_TYPES.has(w)) r += '<span class="tk-ty">' + esc(w) + '</span>';
          else if (e<line.length && line[e]==='(') r += '<span class="tk-fn">' + esc(w) + '</span>';
          else r += esc(w);
          i=e; continue;
        }
        // Operators
        if ('=<>!+-*/%&|^~'.includes(line[i])) { r += '<span class="tk-op">' + esc(line[i]) + '</span>'; i++; continue; }
        r += esc(line[i]); i++;
      }
      return r;
    }).join('\n');
  }

  // ─── Lex / YACC Highlighter ───
  const C_KW = new Set([
    'auto','break','case','char','const','continue','default','do','double',
    'else','enum','extern','float','for','goto','if','int','long','register',
    'return','short','signed','sizeof','static','struct','switch','typedef',
    'union','unsigned','void','volatile','while',
  ]);
  const C_PREPROC = new Set(['#include','#define','#ifdef','#ifndef','#endif','#pragma','#if','#else','#elif','#undef']);

  function highlightLexYacc(code) {
    return code.split('\n').map(line => {
      const trimmed = line.trimStart();

      // Section delimiters %%
      if (trimmed === '%%') {
        return ' '.repeat(line.length - trimmed.length) + '<span class="tk-sec">' + esc(trimmed) + '</span>';
      }
      // Lex definition section delimiters %{ %}
      if (trimmed === '%{' || trimmed === '%}') {
        return ' '.repeat(line.length - trimmed.length) + '<span class="tk-sec">' + esc(trimmed) + '</span>';
      }
      // YACC directives
      if (/^%(token|left|right|nonassoc|type|union|start|expect)/.test(trimmed)) {
        const m = trimmed.match(/^(%\w+)(.*)/);
        if (m) {
          return ' '.repeat(line.length - trimmed.length) + '<span class="tk-pp">' + esc(m[1]) + '</span>' + esc(m[2]);
        }
      }
      // C preprocessor
      if (trimmed.startsWith('#include')) {
        return ' '.repeat(line.length - trimmed.length) + '<span class="tk-pp">' + esc(trimmed) + '</span>';
      }
      // Full-line comments
      if (trimmed.startsWith('//')) {
        return ' '.repeat(line.length - trimmed.length) + '<span class="tk-cmt">' + esc(trimmed) + '</span>';
      }
      if (trimmed.startsWith('/*') || trimmed.startsWith('*') || trimmed.startsWith('*/')) {
        return ' '.repeat(line.length - trimmed.length) + '<span class="tk-cmt">' + esc(trimmed) + '</span>';
      }

      let r = '', i = 0;
      while (i < line.length) {
        // Inline comment
        if (line[i] === '/' && i+1 < line.length && line[i+1] === '/') {
          r += '<span class="tk-cmt">' + esc(line.slice(i)) + '</span>'; break;
        }
        // Strings
        if (line[i] === '"') {
          let e = i + 1;
          while (e < line.length) { if (line[e]==='\\'){e+=2;continue;} if (line[e]==='"'){e++;break;} e++; }
          r += '<span class="tk-str">' + esc(line.slice(i,e)) + '</span>'; i=e; continue;
        }
        if (line[i] === "'") {
          let e = i + 1;
          while (e < line.length) { if (line[e]==='\\'){e+=2;continue;} if (line[e]==="'"){e++;break;} e++; }
          r += '<span class="tk-str">' + esc(line.slice(i,e)) + '</span>'; i=e; continue;
        }
        // Numbers
        if (/[0-9]/.test(line[i]) && (i===0 || /[\s(,\[{:=<>+\-*/%;&|!]/.test(line[i-1]))) {
          let e=i; while(e<line.length && /[0-9.xXa-fA-F]/.test(line[e]))e++;
          r += '<span class="tk-num">' + esc(line.slice(i,e)) + '</span>'; i=e; continue;
        }
        // Identifiers / C keywords
        if (/[a-zA-Z_]/.test(line[i])) {
          let e=i; while(e<line.length && /[a-zA-Z0-9_]/.test(line[e]))e++;
          const w = line.slice(i,e);
          if (C_KW.has(w)) r += '<span class="tk-kw">' + esc(w) + '</span>';
          else if (w === 'printf' || w === 'exit' || w === 'yylex' || w === 'yyparse' || w === 'yyerror' ||
                   w === 'yylval' || w === 'yytext' || w === 'yywrap' || w === 'atoi' || w === 'getlogin')
            r += '<span class="tk-bi">' + esc(w) + '</span>';
          else if (e<line.length && line[e]==='(') r += '<span class="tk-fn">' + esc(w) + '</span>';
          else r += esc(w);
          i=e; continue;
        }
        // $$ $1 $2 $3 etc (YACC actions)
        if (line[i] === '$') {
          let e=i+1; while(e<line.length && /[0-9$]/.test(line[e]))e++;
          r += '<span class="tk-num">' + esc(line.slice(i,e)) + '</span>'; i=e; continue;
        }
        if ('=<>!+-*/%&|^~'.includes(line[i])) { r += '<span class="tk-op">' + esc(line[i]) + '</span>'; i++; continue; }
        r += esc(line[i]); i++;
      }
      return r;
    }).join('\n');
  }

  // ─── Plain text (no highlighting) ───
  function highlightText(code) {
    return esc(code);
  }

  // ─── Router ───
  function getHighlighter(lang) {
    switch (lang) {
      case 'python': return highlightPython;
      case 'java':   return highlightJava;
      case 'lex':    return highlightLexYacc;
      case 'yacc':   return highlightLexYacc;
      default:       return highlightText;
    }
  }

  // Apply highlighting based on data-lang attribute
  document.querySelectorAll('.code-block code').forEach(el => {
    const lang = (el.getAttribute('data-lang') || 'text').toLowerCase();
    const highlighter = getHighlighter(lang);
    el.innerHTML = highlighter(el.textContent);
  });

  // ── 4. Active nav on scroll ──────────────────
  const sections = document.querySelectorAll('.code-section');
  const links    = document.querySelectorAll('.nav-link');
  let ticking = false;
  function updateNav() {
    let cur = '';
    sections.forEach(s => { if (s.getBoundingClientRect().top <= 100) cur = s.id; });
    links.forEach(l => l.classList.toggle('active', l.dataset.target === cur));
  }
  window.addEventListener('scroll', () => {
    if (!ticking) { requestAnimationFrame(() => { updateNav(); ticking = false; }); ticking = true; }
  });
  updateNav();
})();
