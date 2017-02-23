// CodeMirror, copyright (c) by Marijn Haverbeke and others
// Distributed under an MIT license: http://codemirror.net/LICENSE

// Utility function that allows modes to be combined. The mode given
// as the base argument takes care of most of the normal mode
// functionality, but a second (typically simple) mode is used, which
// can override the style of text. Both modes get to parse all of the
// text, but when both assign a non-null style to a piece of code, the
// overlay wins, unless the combine argument was true and not overridden,
// or state.overlay.combineTokens was true, in which case the styles are
// combined.
!function(e){"object"==typeof exports&&"object"==typeof module?e(require("../../lib/codemirror")):"function"==typeof define&&define.amd?define(["../../lib/codemirror"],e):e(CodeMirror)}(function(e){"use strict";e.overlayMode=function(o,r,a){return{startState:function(){return{base:e.startState(o),overlay:e.startState(r),basePos:0,baseCur:null,overlayPos:0,overlayCur:null,streamSeen:null}},copyState:function(a){return{base:e.copyState(o,a.base),overlay:e.copyState(r,a.overlay),basePos:a.basePos,baseCur:null,overlayPos:a.overlayPos,overlayCur:null}},token:function(e,n){return(e!=n.streamSeen||Math.min(n.basePos,n.overlayPos)<e.start)&&(n.streamSeen=e,n.basePos=n.overlayPos=e.start),e.start==n.basePos&&(n.baseCur=o.token(e,n.base),n.basePos=e.pos),e.start==n.overlayPos&&(e.pos=e.start,n.overlayCur=r.token(e,n.overlay),n.overlayPos=e.pos),e.pos=Math.min(n.basePos,n.overlayPos),null==n.overlayCur?n.baseCur:null!=n.baseCur&&n.overlay.combineTokens||a&&null==n.overlay.combineTokens?n.baseCur+" "+n.overlayCur:n.overlayCur},indent:o.indent&&function(e,r){return o.indent(e.base,r)},electricChars:o.electricChars,innerMode:function(e){return{state:e.base,mode:o}},blankLine:function(e){o.blankLine&&o.blankLine(e.base),r.blankLine&&r.blankLine(e.overlay)}}}});