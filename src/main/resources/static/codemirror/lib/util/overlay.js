// Utility function that allows modes to be combined. The mode given
// as the base argument takes care of most of the normal mode
// functionality, but a second (typically simple) mode is used, which
// can override the style of text. Both modes get to parse all of the
// text, but when both assign a non-null style to a piece of code, the
// overlay wins, unless the combine argument was true, in which case
// the styles are combined.

// overlayParser is the old, deprecated name
CodeMirror.overlayMode=CodeMirror.overlayParser=function(r,e,o){return{startState:function(){return{base:CodeMirror.startState(r),overlay:CodeMirror.startState(e),basePos:0,baseCur:null,overlayPos:0,overlayCur:null}},copyState:function(o){return{base:CodeMirror.copyState(r,o.base),overlay:CodeMirror.copyState(e,o.overlay),basePos:o.basePos,baseCur:null,overlayPos:o.overlayPos,overlayCur:null}},token:function(a,s){return a.start==s.basePos&&(s.baseCur=r.token(a,s.base),s.basePos=a.pos),a.start==s.overlayPos&&(a.pos=a.start,s.overlayCur=e.token(a,s.overlay),s.overlayPos=a.pos),a.pos=Math.min(s.basePos,s.overlayPos),a.eol()&&(s.basePos=s.overlayPos=0),null==s.overlayCur?s.baseCur:null!=s.baseCur&&o?s.baseCur+" "+s.overlayCur:s.overlayCur},indent:r.indent&&function(e,o){return r.indent(e.base,o)},electricChars:r.electricChars,innerMode:function(e){return{state:e.base,mode:r}}}};
