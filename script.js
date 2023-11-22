function copyCode(blockNumber) {
  let codeText;

  if (typeof document !== 'undefined') {
    // Running in a browser environment
    const codeBlock = document.querySelector(`.code-block:nth-child(${blockNumber})`);
    if (codeBlock) {
      codeText = codeBlock.querySelector('pre').textContent;
    }
  } else {
    // Running in a Node.js environment
    const jsdom = require('jsdom');
    const { JSDOM } = jsdom;
    const dom = new JSDOM();
    const { document } = dom.window;

    const codeBlock = document.querySelector(`.code-block:nth-child(${blockNumber})`);
    if (codeBlock) {
      codeText = codeBlock.querySelector('pre').textContent;
    }
  }

  if (codeText) {
    // Copy codeText to the clipboard (browser-specific code)
    if (typeof document !== 'undefined') {
      const textarea = document.createElement('textarea');
      textarea.value = codeText;
      document.body.appendChild(textarea);
      textarea.select();
      document.execCommand('copy');
      document.body.removeChild(textarea);

      const copyButton = document.querySelector('.copy-button');
      copyButton.innerHTML = '<span class="tick-mark"></span> Code Copied';
      setTimeout(() => {
        copyButton.innerHTML = '📋 Copy Code';
      }, 2000);
    } else {
      // In a Node.js environment, you can use a clipboard library like 'clipboardy' to copy to clipboard
      const clipboardy = require('clipboardy');
      clipboardy.writeSync(codeText);

      console.log('Code Copied');
    }
  }
}

// Example usage in the browser (adjust your HTML structure as mentioned in the previous response)
/*
<div class="code-block">
  <pre>
    <!-- Code content goes here -->
  </pre>
  <button class="copy-button" onclick="copyCode(1)">📋 Copy Code</button>
</div>
*/

// Example usage in Node.js
// copyCode(1); // Call this function with the desired blockNumber

