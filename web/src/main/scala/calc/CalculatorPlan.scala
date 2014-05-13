package calc

import unfiltered.filter.Plan
import unfiltered.request.{ GET, Path, Params, POST, & }
import unfiltered.response.{ BadRequest, Ok, Html5 }

class CalculatorPlan extends Plan{
  def intent = {
    case GET(Path("/")) => page("", "")

    case POST(Path("/")) & Params(p) =>
      val q = p("q").headOption.getOrElse("")
      Calculator.calculate(q.split(' ')).fold(
        msg => BadRequest ~> page(msg, q),
        i => Ok ~> page(q + " = " + i, ""))
  }

  def page(msg:String, q:String) = Html5(
    <h2>{msg}</h2>
    <form action="/" method="post">
      <input type="text" name="q" value={q}/>
      <input type="submit"/>
    </form>
  <footer>Version {BuildInfo.version}, built {BuildInfo.timestamp}</footer>)
}
