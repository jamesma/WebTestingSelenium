WebTestingSelenium
==================

Author: Jun Yan Ma (ma23@purdue.edu)

Project Description: ProjectDescription.pdf (http://www.cs.purdue.edu/homes/zheng16/490/webtesting.html)

Main method is located at: `src/com/jamesma/webtesting/WebTester.java`

Part 1
======

XPath is used extensively to find WebElements using WebDriver. This is easily accomplished by using the Google Chrome Extension [XPath Helper][].

File containing test cases: `input1`

File containing test results: `output1`

Part 2
======

Nist file conversion utility located at: `src/com/jamesma/webtesting/utils`

fire-eye exported Nist file with strength 2: `input2-nist-str2.txt`

File containing test cases: `input2` (converted from Nist file)

File containing test results: `output2`

Automated Solution Design: `Solution.pdf`

NOTE: Only 2-way (pairwise) test cases are generated from fire-eye, according to instructor's advice.
NOTE: Selenium's WebElement doesn't expose an API to retrieve whitespace-only text fields. That is, WebElement.getText() removes leading and trailing whitespace. As such, Requirement violation checking also removes leading and trailing whitespaces from test cases before performing the check.

If needed, Nist files with strength 3, 4, 5 are located in root folder.



[XPath Helper]: https://chrome.google.com/webstore/detail/xpath-helper/hgimnogjllphhhkhlmebbmlgjoejdpjl "XPath Helper"