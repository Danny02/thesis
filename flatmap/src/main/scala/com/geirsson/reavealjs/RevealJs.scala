package com.geirsson.reavealjs

import scalatags.Text
import scalatags.Text.all._

object RevealJs {

  def header =
    raw("""
          |	<head>
          |		<meta charset="utf-8">
          |
          |		<title>reveal.js – The HTML Presentation Framework</title>
          |
          |		<meta name="description" content="A framework for easily creating beautiful presentations using HTML">
          |		<meta name="author" content="Hakim El Hattab">
          |
          |		<meta name="apple-mobile-web-app-capable" content="yes">
          |		<meta name="apple-mobile-web-app-status-bar-style" content="black-translucent">
          |
          |		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
          |
          |		<link rel="stylesheet" href="css/reveal.css">
          |		<link rel="stylesheet" href="css/theme/black.css" id="theme">
          |
          |		<!-- Theme used for syntax highlighting of code -->
          |		<link rel="stylesheet" href="lib/css/zenburn.css">
          |
          |		<!-- Printing and PDF exports -->
          |		<script>
          |			var link = document.createElement( 'link' );
          |			link.rel = 'stylesheet';
          |			link.type = 'text/css';
          |			link.href = window.location.search.match( /print-pdf/gi ) ? 'css/print/pdf.css' : 'css/print/paper.css';
          |			document.getElementsByTagName( 'head' )[0].appendChild( link );
          |		</script>
          |
          |		<!--[if lt IE 9]>
          |		<script src="lib/js/html5shiv.js"></script>
          |		<![endif]-->
          |	</head>
    """.stripMargin)
  def footer =
    raw("""
          |		<script src="lib/js/head.min.js"></script>
          |		<script src="js/reveal.js"></script>
          |
          |		<script>
          |			// More info https://github.com/hakimel/reveal.js#configuration
          |			Reveal.initialize({
          |				controls: true,
          |				progress: true,
          |				history: true,
          |				center: true,
          |				transition: 'slide', // none/fade/slide/convex/concave/zoom
          |				// More info https://github.com/hakimel/reveal.js#dependencies
          |				dependencies: [
          |					{ src: 'lib/js/classList.js', condition: function() { return !document.body.classList; } },
          |					{ src: 'plugin/markdown/marked.js', condition: function() { return !!document.querySelector( '[data-markdown]' ); } },
          |					{ src: 'plugin/markdown/markdown.js', condition: function() { return !!document.querySelector( '[data-markdown]' ); } },
          |					{ src: 'plugin/highlight/highlight.js', async: true, callback: function() { hljs.initHighlightingOnLoad(); } },
          |					{ src: 'plugin/zoom-js/zoom.js', async: true },
          |					{ src: 'plugin/notes/notes.js', async: true }
          |				]
          |			});
          |		</script>
    """.stripMargin)
  import com.geirsson.scalatags.Tags._
  def skipSlide(tags: Text.Modifier*) = span("")

  def slide(tags: Text.Modifier*) =
    section(Seq(data("background") := "#202020") ++ tags: _*)
//    section(tags: _*)

  def render(slides: Text.all.Frag): String =
    html(
        header,
        body(
            div(
                `class` := "reveal",
                div(
                    `class` := "slides",
                    slides
                )
            ),
            footer
        )
    ).render

  private def fixBrokenIndent(frag: String): String = {
    // Fix broken indentation by scalatex
    val toStrip =
      " " * frag.trim.lines
        .drop(1)
        .withFilter(_.nonEmpty)
        .map(_.takeWhile(_ == ' ').length)
        .min
    frag.lines.map(_.stripPrefix(toStrip)).mkString("\n")
  }

  def highlight(codeToHighlight: String) = {
    pre(
        style := "font-size: 0.46em", // fits 80 characters on column in my machine
        code(
            `class` := "hljs scala",
            contentEdit,
            dataTrim,
            fixBrokenIndent(codeToHighlight)
        )
    )
  }
}